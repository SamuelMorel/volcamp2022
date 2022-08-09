package org.volcamp.api.controller.rest;

import org.jboss.resteasy.reactive.RestResponse;
import org.volcamp.api.controller.rest.generic.AbstractRestController;
import org.volcamp.api.entity.Session;
import org.volcamp.api.repository.SessionRepository;
import org.volcamp.api.utils.Pageable;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("sessions")
public class SessionController extends AbstractRestController<Session> {
    @Inject
    public SessionController(SessionRepository sessionRepository) {
        super(sessionRepository);
    }

    @GET
    public RestResponse<Pageable<Session>> findList(
            @BeanParam Session entity,
            @QueryParam("index") @DefaultValue("0") Integer index,
            @QueryParam("size") @DefaultValue("10") Integer size,
            @QueryParam("sort") @DefaultValue("") String sortColumn,
            @QueryParam("asc") @DefaultValue("true") Boolean ascending) {
        return RestResponse.ok(repository.findList(entity, index, size, sortColumn, ascending));
    }
}
