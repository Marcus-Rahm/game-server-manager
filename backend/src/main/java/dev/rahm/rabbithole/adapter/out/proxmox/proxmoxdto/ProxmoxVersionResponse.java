package dev.rahm.rabbithole.adapter.out.proxmox.proxmoxdto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProxmoxVersionResponse {
    @JsonProperty("data") // The actual version data is nested under "data"
    public ProxmoxVersionData data;

    public static class ProxmoxVersionData {
        @JsonProperty("release")
        public String release;
        @JsonProperty("repover")
        public String repover;
        @JsonProperty("version")
        public String version; // e.g., "7.4-15"
    }
}
