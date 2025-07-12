package dev.rahm.rabbithole.domain.service;

import dev.rahm.rabbithole.domain.model.OrchestrationPlatformDetails;
import dev.rahm.rabbithole.domain.model.VirtualMachine;
import dev.rahm.rabbithole.domain.model.VmProvisionRequest;
import dev.rahm.rabbithole.domain.ports.out.InfrastructureProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class InfrastructureOrchestrationService {
    @Inject
    InfrastructureProvider infrastructureProvider;

    public OrchestrationPlatformDetails retrievePlatformInformation() {
        return infrastructureProvider.getPlatformDetails();
    }

    public List<VirtualMachine> getVirtualMachines(String nodeName) {
        return infrastructureProvider.listVirtualMachines(nodeName);
    }

    public Long getNextVmId() {
        return infrastructureProvider.getNextAvailableVmId();
    }

    public VirtualMachine provisionNewVm(VmProvisionRequest request, String nodeName) {
        return infrastructureProvider.provisionVm(request, nodeName);
    }
}
