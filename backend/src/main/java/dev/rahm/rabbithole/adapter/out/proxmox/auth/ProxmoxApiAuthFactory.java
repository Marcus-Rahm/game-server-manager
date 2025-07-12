package dev.rahm.rabbithole.adapter.out.proxmox.auth;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

@ApplicationScoped
public class ProxmoxApiAuthFactory implements ClientHeadersFactory {
    @ConfigProperty(name = "proxmox.api.user")
    String proxmoxApiUser; // e.g., "rabbithole-api"

    @ConfigProperty(name = "proxmox.api.realm")
    String proxmoxApiRealm; // e.g., "pam"

    @ConfigProperty(name = "proxmox.api.token.id")
    String proxmoxApiTokenId; // e.g., "backend-orchestrator-token"

    @ConfigProperty(name = "proxmox.api.token.secret")
    String proxmoxApiTokenSecret; // The UUID string

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders, MultivaluedMap<String, String> clientOutgoingHeaders) {
        MultivaluedMap<String, String> updatedHeaders = new MultivaluedHashMap<>();

        // Construct the PVEAPIToken string
        String authValue = String.format("PVEAPIToken=%s@%s!%s=%s",
                proxmoxApiUser, proxmoxApiRealm, proxmoxApiTokenId, proxmoxApiTokenSecret);

        // Add the Authorization header
        updatedHeaders.add("Authorization", authValue);

        return updatedHeaders;
    }
}
