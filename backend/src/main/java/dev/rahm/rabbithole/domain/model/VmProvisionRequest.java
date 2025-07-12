package dev.rahm.rabbithole.domain.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record VmProvisionRequest(
        @NotBlank(message = "VM name cannot be blank")
        String vmName,

        @Min(value = 1, message = "CPU cores must be at least 1")
        int cpuCores,

        @Min(value = 512, message = "RAM must be at least 512MB") // Use MB for more granularity
        int ramMB, // Changed to MB for Proxmox compatibility

        @Min(value = 10, message = "Disk size must be at least 10GB")
        int diskGB,

        String sshUsername,

        Long cloudTemplateId // e.g., '100' for Proxmox template VMID
) {}
