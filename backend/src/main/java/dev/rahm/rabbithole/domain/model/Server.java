package dev.rahm.rabbithole.domain.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity // Marks this class as a JPA entity
@Table(name = "game_servers") // Explicitly names the database table
public class Server {
    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementing ID
    private Long id;

    @Column(nullable = false, unique = true) // Server name must be unique and not null
    private String name;

    @Column(unique = true) // IP address should ideally be unique
    private String ipAddress;

    @Column(unique = true) // Provider-specific VMID (e.g., Proxmox VMID)
    private String providerVmId; // Storing as String for flexibility (some might be UUIDs)

    @Enumerated(EnumType.STRING) // Store enum names as strings in DB
    @Column(nullable = false)
    private ServerStatus status;

    @Enumerated(EnumType.STRING) // Store enum names as strings in DB
    @Column(nullable = false)
    private CloudProviderType providerType;

    @Column(nullable = false)
    private int cpuCores;

    @Column(nullable = false)
    private int ramGB; // RAM in Gigabytes

    @Column(nullable = false)
    private int diskGB; // Disk space in Gigabytes

    @Column // Proxmox node name, or other provider's host identifier
    private String hostIdentifier;

    @Column(nullable = false)
    private String sshUsername; // e.g., "root", "ubuntu"

    // Path to the SSH private key used to connect to this specific server
    // This will be a reference to a secure location (e.g., file path, or key ID in vault)
    // Actual key content should NOT be stored directly in the DB for security reasons.
    @Column(nullable = false)
    private String sshPrivateKeyPath;


    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    // Constructors
    public Server() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    // --- Getters and Setters ---
    // You can use Lombok for these, but defining them explicitly for clarity here

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getProviderVmId() { return providerVmId; }
    public void setProviderVmId(String providerVmId) { this.providerVmId = providerVmId; }

    public ServerStatus getStatus() { return status; }
    public void setStatus(ServerStatus status) { this.status = status; }

    public CloudProviderType getProviderType() { return providerType; }
    public void setProviderType(CloudProviderType providerType) { this.providerType = providerType; }

    public int getCpuCores() { return cpuCores; }
    public void setCpuCores(int cpuCores) { this.cpuCores = cpuCores; }

    public int getRamGB() { return ramGB; }
    public void setRamGB(int ramGB) { this.ramGB = ramGB; }

    public int getDiskGB() { return diskGB; }
    public void setDiskGB(int diskGB) { this.diskGB = diskGB; }

    public String getHostIdentifier() { return hostIdentifier; }
    public void setHostIdentifier(String hostIdentifier) { this.hostIdentifier = hostIdentifier; }

    public String getSshUsername() { return sshUsername; }
    public void setSshUsername(String sshUsername) { this.sshUsername = sshUsername; }

    public String getSshPrivateKeyPath() { return sshPrivateKeyPath; }
    public void setSshPrivateKeyPath(String sshPrivateKeyPath) { this.sshPrivateKeyPath = sshPrivateKeyPath; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    // Optional: Override toString, equals, hashCode for better logging/comparison
    @Override
    public String toString() {
        return "Server{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", providerVmId='" + providerVmId + '\'' +
                ", status=" + status +
                ", providerType=" + providerType +
                ", hostIdentifier='" + hostIdentifier + '\'' +
                '}';
    }
}
