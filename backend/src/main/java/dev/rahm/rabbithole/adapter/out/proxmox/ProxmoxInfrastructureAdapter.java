package dev.rahm.rabbithole.adapter.out.proxmox;

import dev.rahm.rabbithole.adapter.out.proxmox.proxmoxdto.*;
import dev.rahm.rabbithole.domain.model.OrchestrationPlatformDetails;
import dev.rahm.rabbithole.domain.model.VirtualMachine;
import dev.rahm.rabbithole.domain.model.VmProvisionRequest;
import dev.rahm.rabbithole.domain.ports.out.InfrastructureProvider;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Form;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Driven Adapter for Proxmox REST API.
 * Implements the generic InfrastructureProvider port.
 * Responsible for translating between the generic domain model and Proxmox API DTOs.
 */
@ApplicationScoped
public class ProxmoxInfrastructureAdapter implements InfrastructureProvider {
    @Inject
    @RestClient // Inject the actual REST client here
    ProxmoxRestClient proxmoxRestClient;

    @ConfigProperty(name = "rabbithole.vm.ssh.public-key-path")
    String sshPublicKeyPath;

    @Override
    public OrchestrationPlatformDetails getPlatformDetails() {
        ProxmoxVersionResponse response = proxmoxRestClient.getVersion();
        // Translate the Proxmox-specific DTO to your generic domain model
        return new OrchestrationPlatformDetails(
                "Proxmox VE", // This is where the adapter knows it's Proxmox
                response.data.release,
                response.data.version, // Use Proxmox 'version' as API version for now
                "Operational" // Or deduce status from a health check if available
        );
    }

    @Override
    public List<VirtualMachine> listVirtualMachines(String nodeName) {
        // Call the Proxmox-specific REST client
        ProxmoxNodeVmListResponse response = proxmoxRestClient.getVmsForNode(nodeName);

        // Translate ProxmoxVmDto list to generic VirtualMachine domain model list
        return response.data.stream()
                .map(dto -> new VirtualMachine(
                        dto.vmid,       // Use vmid as ID
                        dto.name,
                        dto.status,
                        "proxmox",      // Provider ID, hardcoded for now
                        dto.cpu,
                        dto.mem,
                        dto.maxmem,
                        dto.disk,
                        dto.maxdisk,
                        dto.uptime,
                        nodeName        // Pass the node name through
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Long getNextAvailableVmId() {
        ProxmoxNextIdResponse response = proxmoxRestClient.getNextId();
        return response.data;
    }

    @Override
    public VirtualMachine provisionVm(VmProvisionRequest request, String nodeName) {
        Long nextVmId = getNextAvailableVmId(); // Get a new VMID from Proxmox
        ProxmoxTaskResponse taskResponse;

        if (request.cloudTemplateId() != null) { // Assume cloudTemplateId indicates a clone operation
            Log.info("Detected cloudTemplateId. Attempting to clone VM from template " + request.cloudTemplateId() +
                    " to new VMID " + nextVmId + " on node " + nodeName);

            String publicKeyContent = getPublicKeyContent(); // Need for cloud-init in clone

            // Build DTO for cloning with fluent setters
            ProxmoxCloneVmRequestDto cloneRequestDto = new ProxmoxCloneVmRequestDto()
                    .newVmId(nextVmId)
                    .vmName(request.vmName())
                    .fullClone(1) // Full clone recommended for production VMs
                    // .storage("local-lvm") // Uncomment if you want to specify target storage
                    ;

            Log.debug("Clone request DTO: " + cloneRequestDto);

            taskResponse = proxmoxRestClient.cloneVm(
                    nodeName,
                    request.cloudTemplateId(),
                    cloneRequestDto
            );
        }
        else {
            Log.info("No cloudTemplateId. Attempting to create new VM (from scratch) with vmid=" + nextVmId + " on node=" + nodeName);

            ProxmoxCreateVmRequestDto createDto = new ProxmoxCreateVmRequestDto()
                    .vmid(nextVmId)
                    .name(request.vmName())
                    .memory((long) request.ramMB()) // Convert MB to Long
                    .sockets(1)
                    .cores(request.cpuCores())
                    .net0("virtio,bridge=vmbr0")
                    .ide0("local-lvm:" + request.diskGB() + ",format=raw")
                    .bootdisk("ide0")
                    .ostype("l26")
                    .ipconfig0("ip=dhcp");

            Log.debug("Create VM request formData: " + createDto);

            taskResponse = proxmoxRestClient.createVm(
                    nodeName,
                    createDto
            );
        }

        if (taskResponse == null || taskResponse.data == null || taskResponse.data.isEmpty()) {
            throw new RuntimeException("Failed to initiate Proxmox VM operation: No task ID returned.");
        }

        return new VirtualMachine(
                String.valueOf(nextVmId),
                request.vmName(),
                "PROVISIONING", // Initial status
                "proxmox", // Provider ID
                0.0, // CPU usage (initial)
                (long) request.ramMB() * 1024 * 1024, // RAM in bytes (initial)
                (long) request.ramMB() * 1024 * 1024, // Max RAM in bytes (initial)
                (long) request.diskGB() * 1024 * 1024 * 1024, // Disk in bytes (initial)
                (long) request.diskGB() * 1024 * 1024 * 1024, // Max Disk in bytes (initial)
                0L, // Uptime (initial)
                nodeName
        );
    }

    private String getPublicKeyContent() {
        try {
            return Files.readString(Paths.get(sshPublicKeyPath)).trim();
        } catch (IOException e) {
            // Log the error properly in a real application
            System.err.println("Error reading SSH public key: " + e.getMessage());
            throw new RuntimeException("Failed to read SSH public key for VM provisioning.", e);
        }
    }

}
