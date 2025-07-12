package dev.rahm.rabbithole.adapter.out.proxmox.proxmoxdto;

import jakarta.ws.rs.FormParam;

public class ProxmoxCloneVmRequestDto {

    @FormParam("newid")
    public Long newVmId;

    @FormParam("name")
    public String vmName;

    @FormParam("full")
    public Integer fullClone;

    @FormParam("storage")
    public String storage;

    public ProxmoxCloneVmRequestDto() {
    }

    public ProxmoxCloneVmRequestDto newVmId(Long newVmId) {
        this.newVmId = newVmId;
        return this;
    }

    public ProxmoxCloneVmRequestDto vmName(String vmName) {
        this.vmName = vmName;
        return this;
    }

    public ProxmoxCloneVmRequestDto fullClone(Integer fullClone) {
        this.fullClone = fullClone;
        return this;
    }

    public ProxmoxCloneVmRequestDto storage(String storage) {
        this.storage = storage;
        return this;
    }
}
