package org.volcamp.api.exception.mappers;

import io.quarkus.arc.Priority;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(1)
public final class ClientExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException exception) {
        // some code to log the detail of the error before returning the status code only,
        return exception.getResponse();
    }
}
