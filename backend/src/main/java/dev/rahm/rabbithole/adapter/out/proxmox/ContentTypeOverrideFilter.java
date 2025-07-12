package dev.rahm.rabbithole.adapter.out.proxmox;

import io.quarkus.logging.Log;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider // Important: makes JAX-RS discoverable
public class ContentTypeOverrideFilter implements ClientRequestFilter {

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        // Remove any existing Content-Type header
        requestContext.getHeaders().remove(HttpHeaders.CONTENT_TYPE);

        // Add the correct Content-Type header
        requestContext.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED);

        Log.debug("ContentTypeOverrideFilter: Set Content-Type to " + MediaType.APPLICATION_FORM_URLENCODED +
                " for request to " + requestContext.getUri());
        Log.debug("ContentTypeOverrideFilter: All headers after filter: " + requestContext.getStringHeaders());
    }
}
