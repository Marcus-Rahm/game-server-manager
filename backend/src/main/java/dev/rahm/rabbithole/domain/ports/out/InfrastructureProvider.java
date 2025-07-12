package dev.rahm.rabbithole.domain.ports.out;

import dev.rahm.rabbithole.domain.model.OrchestrationPlatformDetails;
import dev.rahm.rabbithole.domain.model.VirtualMachine;
import dev.rahm.rabbithole.domain.model.VmProvisionRequest;

import java.util.List;

public interface InfrastructureProvider {
    OrchestrationPlatformDetails getPlatformDetails();
    List<VirtualMachine> listVirtualMachines(String nodeName);
    Long getNextAvailableVmId();
    /**
     * Provisions a new virtual machine.
     * @param request The generic VM provisioning request.
     * @param nodeName The name of the node where the VM should be provisioned.
     * @return The provisioned VirtualMachine domain object.
     */
    VirtualMachine provisionVm(VmProvisionRequest request, String nodeName);
}
