package org.volcamp.api.utils.token;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/")
@RegisterRestClient(configKey = "oidc-rest-client")
public interface OidcRestClient {

    @POST
    RestResponse<String> login(UserPasswordInput.TokenRequestDTO auth);
}
