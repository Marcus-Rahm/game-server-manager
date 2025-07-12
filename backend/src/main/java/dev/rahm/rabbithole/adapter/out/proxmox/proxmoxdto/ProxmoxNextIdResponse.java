package dev.rahm.rabbithole.adapter.out.proxmox.proxmoxdto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProxmoxNextIdResponse {
    @JsonProperty("data")
    public Long data; // The next available VMID is a simple long value
}
