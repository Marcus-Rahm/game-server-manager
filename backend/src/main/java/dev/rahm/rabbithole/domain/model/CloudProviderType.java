package dev.rahm.rabbithole.domain.model;

public enum CloudProviderType {
    PROXMOX,
    HETZNER,
    AWS,
    AZURE,
    GCP,
    LOCAL // For local development/testing instances
}
