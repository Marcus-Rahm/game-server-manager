package dev.rahm.rabbithole.adapter.in.rest;

import dev.rahm.rabbithole.domain.model.OrchestrationPlatformDetails;
import dev.rahm.rabbithole.domain.model.VirtualMachine;
import dev.rahm.rabbithole.domain.model.VmProvisionRequest;
import dev.rahm.rabbithole.domain.service.InfrastructureOrchestrationService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/infrastructure")
public class InfrastructureRest {
    @Inject
    InfrastructureOrchestrationService orchestrationService;

    @GET
    @Path("/platform-details")
    @Produces(MediaType.APPLICATION_JSON)
    public OrchestrationPlatformDetails getPlatformDetails() {
        return orchestrationService.retrievePlatformInformation();
    }

    @GET
    @Path("/nodes/{nodeName}/vms")
    @Produces(MediaType.APPLICATION_JSON)
    public List<VirtualMachine> getVmsForNode(@PathParam("nodeName") String nodeName) {
        return orchestrationService.getVirtualMachines(nodeName);
    }

    @GET
    @Path("/next-vmid")
    @Produces(MediaType.APPLICATION_JSON)
    public Long getNextVmId() {
        return orchestrationService.getNextVmId();
    }

    @POST
    @Path("/nodes/{nodeName}/vms") // POST to /infrastructure/nodes/{nodeName}/vms
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // @Valid will trigger validation on VmProvisionRequest fields if you added hibernate-validator
    public VirtualMachine provisionVm(@PathParam("nodeName") String nodeName, @Valid VmProvisionRequest request) {
        System.out.println("Received VM provision request for node: " + nodeName + " and VM: " + request.vmName());
        return orchestrationService.provisionNewVm(request, nodeName);
    }
}
