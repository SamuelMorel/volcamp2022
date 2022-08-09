package org.volcamp.api.utils.token;

import com.querydsl.core.util.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestResponse;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/token")
public class TokenController {

    @ConfigProperty(name = "quarkus.oidc.client-id")
    String clientId;

    @ConfigProperty(name = "quarkus.oidc.credentials.secret")
    String clientSecret;

    @ConfigProperty(name = "quarkus.oidc.audience")
    String audience;

    @RestClient
    OidcRestClient oidcRestClient;

    @POST
    @Path("/login")
    @PermitAll
    public RestResponse<String> login(UserPasswordInput auth) {
        if (auth == null || StringUtils.isNullOrEmpty(auth.getLogin()) || StringUtils.isNullOrEmpty(auth.getPassword())) {
            return RestResponse.ResponseBuilder.create(
                    RestResponse.Status.BAD_REQUEST, "must provide login and password"
            ).build();
        }

        return oidcRestClient.login(auth.toTokenRequestDTO(clientId, clientSecret, audience));
    }
}
