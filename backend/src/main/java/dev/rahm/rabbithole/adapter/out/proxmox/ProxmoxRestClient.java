package dev.rahm.rabbithole.adapter.out.proxmox;

import dev.rahm.rabbithole.adapter.out.proxmox.auth.ProxmoxApiAuthFactory;
import dev.rahm.rabbithole.adapter.out.proxmox.proxmoxdto.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "proxmox-api")
@RegisterClientHeaders(ProxmoxApiAuthFactory.class)
@Path("/api2/json")
public interface ProxmoxRestClient {
    @GET
    @Path("/version")
    @Produces(MediaType.APPLICATION_JSON)
    ProxmoxVersionResponse getVersion();

    @GET
    @Path("/nodes/{node}/qemu") // Proxmox uses 'qemu' for KVM VMs
    @Produces(MediaType.APPLICATION_JSON)
    ProxmoxNodeVmListResponse getVmsForNode(@PathParam("node") String nodeName);

    @GET
    @Path("/cluster/nextid")
    @Produces(MediaType.APPLICATION_JSON)
    ProxmoxNextIdResponse getNextId();

    /**
     * Creates a new QEMU VM by cloning a template.
     * Corresponds to POST /nodes/{node}/qemu
     *
     * Proxmox usually returns a task UPID for async operations.
     */
    @POST
    @Path("/nodes/{node}/qemu")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED) // Proxmox often expects form data
    @Produces(MediaType.APPLICATION_JSON)
    ProxmoxTaskResponse createVm(@PathParam("node") String nodeName, ProxmoxCreateVmRequestDto requestDto);

    @POST
    @Path("/nodes/{node}/qemu/{templateVmid}/clone")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    ProxmoxTaskResponse cloneVm(
            @PathParam("node") String nodeName,
            @PathParam("templateVmid") Long templateVmid,
            ProxmoxCloneVmRequestDto requestDto
    );


}
