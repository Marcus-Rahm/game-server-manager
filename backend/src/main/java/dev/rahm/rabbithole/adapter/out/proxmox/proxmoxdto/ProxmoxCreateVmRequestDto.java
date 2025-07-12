package dev.rahm.rabbithole.adapter.out.proxmox.proxmoxdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.ws.rs.FormParam;

public class ProxmoxCreateVmRequestDto {
    @FormParam("vmid")
    public Long vmid; // VMID is mandatory for creation via REST

    @FormParam("name")
    public String name;

    @FormParam("memory")
    public Long memory; // RAM in MB

    @FormParam("sockets")
    public Integer sockets; // Number of CPU sockets

    @FormParam("cores")
    public Integer cores; // Number of cores per socket

    @FormParam("net0")
    public String net0; // Network configuration, e.g., "virtio,bridge=vmbr0"

    @FormParam("ide0")
    public String ide0; // Disk configuration, e.g., "local-lvm:10,format=raw"

    @FormParam("bootdisk")
    public String bootdisk; // Which disk to boot from, e.g., "ide0"

    @FormParam("ostype")
    public String ostype; // OS type, e.g., "l26" for Linux

    @FormParam("ipconfig0")
    public String ipconfig0; // Cloud-init IP configuration, e.g., "ip=dhcp"

    // Default constructor
    public ProxmoxCreateVmRequestDto() {}

    // Builder pattern or fluent setters are good for DTOs if many fields
    public ProxmoxCreateVmRequestDto vmid(Long vmid) { this.vmid = vmid; return this; }
    public ProxmoxCreateVmRequestDto name(String name) { this.name = name; return this; }
    public ProxmoxCreateVmRequestDto memory(Long memory) { this.memory = memory; return this; }
    public ProxmoxCreateVmRequestDto sockets(Integer sockets) { this.sockets = sockets; return this; }
    public ProxmoxCreateVmRequestDto cores(Integer cores) { this.cores = cores; return this; }
    public ProxmoxCreateVmRequestDto net0(String net0) { this.net0 = net0; return this; }
    public ProxmoxCreateVmRequestDto ide0(String ide0) { this.ide0 = ide0; return this; }
    public ProxmoxCreateVmRequestDto bootdisk(String bootdisk) { this.bootdisk = bootdisk; return this; }
    public ProxmoxCreateVmRequestDto ostype(String ostype) { this.ostype = ostype; return this; }
    public ProxmoxCreateVmRequestDto ipconfig0(String ipconfig0) { this.ipconfig0 = ipconfig0; return this; }
}
