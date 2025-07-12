package dev.rahm.rabbithole.adapter.out.proxmox;

import dev.rahm.rabbithole.adapter.out.proxmox.proxmoxdto.ProxmoxNextIdResponse;
import dev.rahm.rabbithole.adapter.out.proxmox.proxmoxdto.ProxmoxNodeVmListResponse;
import dev.rahm.rabbithole.adapter.out.proxmox.proxmoxdto.ProxmoxVersionResponse;
import dev.rahm.rabbithole.domain.model.OrchestrationPlatformDetails;
import dev.rahm.rabbithole.domain.model.VirtualMachine;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
public class ProxmoxInfrastructureAdapterTest {

    @Inject
    ProxmoxInfrastructureAdapter proxmoxInfrastructureAdapter;

    @InjectMock
    @RestClient
    ProxmoxRestClient mockProxmoxRestClient;

    @BeforeEach
    void setup() {
        Mockito.reset(mockProxmoxRestClient); // Reset mocks before each test
    }

    @Test
    void getNextAvailableVmId_shouldReturnCorrectId() {
        // Arrange
        Long expectedNextId = 101L;
        ProxmoxNextIdResponse mockResponse = new ProxmoxNextIdResponse();
        mockResponse.data = expectedNextId;
        when(mockProxmoxRestClient.getNextId()).thenReturn(mockResponse);

        // Act
        Long actualNextId = proxmoxInfrastructureAdapter.getNextAvailableVmId();

        // Assert
        assertEquals(expectedNextId, actualNextId, "The returned VMID should match the mocked value.");
        verify(mockProxmoxRestClient).getNextId(); // Verify interaction with mock
    }

    @Test
    void getPlatformDetails_shouldMapProxmoxVersionToDomainModel() {
        // Arrange
        ProxmoxVersionResponse mockProxmoxVersion = new ProxmoxVersionResponse();
        mockProxmoxVersion.data = new ProxmoxVersionResponse.ProxmoxVersionData();
        mockProxmoxVersion.data.release = "8.1";
        mockProxmoxVersion.data.repover = "pve-manager/8.1.4";
        mockProxmoxVersion.data.version = "8.1.4-2";

        when(mockProxmoxRestClient.getVersion()).thenReturn(mockProxmoxVersion);

        // Act
        OrchestrationPlatformDetails platformDetails = proxmoxInfrastructureAdapter.getPlatformDetails();

        // Assert
        assertEquals("Proxmox VE", platformDetails.name(), "Platform name should be Proxmox VE.");
        assertEquals("8.1", platformDetails.version(), "Release version should match.");
        assertEquals("8.1.4-2", platformDetails.apiVersion(), "API version should match.");
        assertEquals("Operational", platformDetails.status(), "Status should be operational.");
        verify(mockProxmoxRestClient).getVersion(); // Verify interaction with mock
    }

    @Test
    void listVirtualMachines_shouldMapProxmoxVmsToDomainModels() {
        // Arrange
        String testNode = "test-node";
        ProxmoxNodeVmListResponse mockVmListResponse = new ProxmoxNodeVmListResponse();
        mockVmListResponse.data = Arrays.asList(
                createMockVmDto("100", "test-vm-1", "running", 0.5, 1024L, 2048L, 512L, 1024L, 3600L),
                createMockVmDto("102", "test-vm-2", "stopped", 0.0, 0L, 0L, 0L, 0L, 0L)
        );

        when(mockProxmoxRestClient.getVmsForNode(anyString())).thenReturn(mockVmListResponse);

        // Act
        List<VirtualMachine> vms = proxmoxInfrastructureAdapter.listVirtualMachines(testNode);

        // Assert
        assertFalse(vms.isEmpty(), "VM list should not be empty.");
        assertEquals(2, vms.size(), "Should return 2 VMs.");

        VirtualMachine vm1 = vms.get(0);
        assertEquals("100", vm1.id());
        assertEquals("test-vm-1", vm1.name());
        assertEquals("running", vm1.status());
        assertEquals(0.5, vm1.cpuUsage());
        assertEquals(testNode, vm1.nodeName()); // Verify node name is passed through

        VirtualMachine vm2 = vms.get(1);
        assertEquals("102", vm2.id());
        assertEquals("test-vm-2", vm2.name());
        assertEquals("stopped", vm2.status());
        assertEquals(0.0, vm2.cpuUsage());
        assertEquals(testNode, vm2.nodeName());

        verify(mockProxmoxRestClient).getVmsForNode(testNode); // Verify interaction with mock
    }

    // Helper method to create mock ProxmoxVmDto for readability
    private ProxmoxNodeVmListResponse.ProxmoxVmDto createMockVmDto(
            String vmid, String name, String status, Double cpu, Long mem, Long maxmem, Long disk, Long maxdisk, Long uptime) {
        ProxmoxNodeVmListResponse.ProxmoxVmDto dto = new ProxmoxNodeVmListResponse.ProxmoxVmDto();
        dto.vmid = vmid;
        dto.name = name;
        dto.status = status;
        dto.cpu = cpu;
        dto.mem = mem;
        dto.maxmem = maxmem;
        dto.disk = disk;
        dto.maxdisk = maxdisk;
        dto.uptime = uptime;
        return dto;
    }
}
