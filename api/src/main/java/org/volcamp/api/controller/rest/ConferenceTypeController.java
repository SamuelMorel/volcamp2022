package org.volcamp.api.controller.rest;

import org.jboss.resteasy.reactive.RestResponse;
import org.volcamp.api.controller.rest.generic.AbstractRestController;
import org.volcamp.api.entity.ConferenceType;
import org.volcamp.api.entity.Session;
import org.volcamp.api.repository.ConferenceTypeRepository;
import org.volcamp.api.utils.Pageable;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("conference-types")
public class ConferenceTypeController extends AbstractRestController<ConferenceType> {
    @Inject
    public ConferenceTypeController(ConferenceTypeRepository conferenceTypeRepository) {
        super(conferenceTypeRepository);
    }

    @GET
    public RestResponse<Pageable<ConferenceType>> findList(
            @BeanParam ConferenceType entity,
            @QueryParam("index") @DefaultValue("0") Integer index,
            @QueryParam("size") @DefaultValue("10") Integer size,
            @QueryParam("sort") @DefaultValue("") String sortColumn,
            @QueryParam("asc") @DefaultValue("true") Boolean ascending) {
        return RestResponse.ok(repository.findList(entity, index, size, sortColumn, ascending));
    }

    @GET
    @Path("{id}/sessions")
    public RestResponse<List<Session>> tagTalks(@PathParam("id") UUID conferanceTypeId) {
        ConferenceType conferenceType = repository.findById(conferanceTypeId);
        return conferenceType != null ?
                RestResponse.ok(conferenceType.getSessions()) :
                RestResponse.notFound();
    }
}
