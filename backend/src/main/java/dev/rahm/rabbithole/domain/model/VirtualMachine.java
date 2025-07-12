package dev.rahm.rabbithole.domain.model;


/**
 * A generic domain model representing a virtual machine.
 * It does not contain any provider-specific (e.g., Proxmox) fields.
 */
public record VirtualMachine(
        String id,
        String name,
        String status, // e.g., "running", "stopped", "suspended"
        String providerId, // Optional: useful for mapping back to provider
        Double cpuUsage, // e.g., 0.5 (for 50%)
        Long memoryUsageBytes,
        Long memoryMaxBytes,
        Long diskUsageBytes,
        Long diskMaxBytes,
        Long uptimeSeconds,
        String nodeName // The node it's running on (could be a separate Node domain object later)
) {}
