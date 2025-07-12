package dev.rahm.rabbithole.adapter.out.proxmox.proxmoxdto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProxmoxTaskResponse {
    @JsonProperty("data")
    public String data; // This 'data' field will contain the UPID string
    @JsonProperty("error")
    public String error;
}
