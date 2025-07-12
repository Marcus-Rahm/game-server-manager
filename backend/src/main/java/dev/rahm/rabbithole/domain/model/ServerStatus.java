package dev.rahm.rabbithole.domain.model;

public enum ServerStatus {
    PROVISIONING, // Server creation in progress on the cloud provider
    RUNNING,      // Server is active and operational
    STOPPED,      // Server is powered off
    SUSPENDED,    // Server is in a suspended state
    DELETING,     // Server deletion in progress
    ERROR,        // An error occurred during server lifecycle
    UNKNOWN       // Status cannot be determined
}
