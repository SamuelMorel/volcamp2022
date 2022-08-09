package org.volcamp.api.controller.rest;

import org.jboss.resteasy.reactive.RestResponse;
import org.volcamp.api.controller.rest.generic.AbstractRestController;
import org.volcamp.api.entity.Author;
import org.volcamp.api.entity.Tag;
import org.volcamp.api.entity.Talk;
import org.volcamp.api.repository.TalkRepository;
import org.volcamp.api.utils.Pageable;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("talks")
public class TalkController extends AbstractRestController<Talk> {
    @Inject
    public TalkController(TalkRepository talkRepository) {
        super(talkRepository);
    }

    @GET
    public RestResponse<Pageable<Talk>> findList(
            @BeanParam Talk entity,
            @QueryParam("index") @DefaultValue("0") Integer index,
            @QueryParam("size") @DefaultValue("10") Integer size,
            @QueryParam("sort") @DefaultValue("") String sortColumn,
            @QueryParam("asc") @DefaultValue("true") Boolean ascending) {
        return RestResponse.ok(repository.findList(entity, index, size, sortColumn, ascending));
    }

    @GET
    @Path("{id}/authors")
    public RestResponse<List<Author>> talkAuthors(@PathParam("id") UUID talkId) {
        Talk talk = repository.findById(talkId);
        return talk != null ?
                RestResponse.ok(talk.getAuthors()) :
                RestResponse.notFound();
    }

    @GET
    @Path("{id}/tags")
    public RestResponse<List<Tag>> talkTags(@PathParam("id") UUID talkId) {
        Talk talk = repository.findById(talkId);
        return talk != null ?
                RestResponse.ok(talk.getTags()) :
                RestResponse.notFound();
    }
}
