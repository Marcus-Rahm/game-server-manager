package dev.rahm.rabbithole.domain.model;

public record OrchestrationPlatformDetails (
        String name, // e.g., "Proxmox VE" or "Hetzner Cloud"
        String version,
        String apiVersion,
        String status // e.g., "Operational"
) {}
