package dev.rahm.rabbithole.adapter.out.proxmox.proxmoxdto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProxmoxNodeVmListResponse {
    @JsonProperty("data")
    public List<ProxmoxVmDto> data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProxmoxVmDto {
        @JsonProperty("vmid")
        public String vmid;
        @JsonProperty("name")
        public String name;
        @JsonProperty("status") // e.g., "running", "stopped"
        public String status;
        @JsonProperty("cpu")
        public Double cpu; // CPU usage percentage
        @JsonProperty("mem")
        public Long mem; // Memory usage in bytes
        @JsonProperty("maxmem")
        public Long maxmem; // Max memory in bytes
        @JsonProperty("disk")
        public Long disk; // Disk usage in bytes
        @JsonProperty("maxdisk")
        public Long maxdisk; // Max disk in bytes
        @JsonProperty("uptime")
        public Long uptime; // Uptime in seconds
        // Add more fields as needed from Proxmox API response
    }
}
