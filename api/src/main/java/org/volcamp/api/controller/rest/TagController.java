package org.volcamp.api.controller.rest;

import org.jboss.resteasy.reactive.RestResponse;
import org.volcamp.api.controller.rest.generic.AbstractRestController;
import org.volcamp.api.entity.Tag;
import org.volcamp.api.entity.Talk;
import org.volcamp.api.repository.TagRepository;
import org.volcamp.api.utils.Pageable;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("tags")
public class TagController extends AbstractRestController<Tag> {
    @Inject
    public TagController(TagRepository tagRepository) {
        super(tagRepository);
    }

    @GET
    public RestResponse<Pageable<Tag>> findList(
            @BeanParam Tag entity,
            @QueryParam("index") @DefaultValue("0") Integer index,
            @QueryParam("size") @DefaultValue("10") Integer size,
            @QueryParam("sort") @DefaultValue("") String sortColumn,
            @QueryParam("asc") @DefaultValue("true") Boolean ascending) {
        return RestResponse.ok(repository.findList(entity, index, size, sortColumn, ascending));
    }

    @GET
    @Path("{id}/talks")
    public RestResponse<List<Talk>> tagTalks(@PathParam("id") UUID tagId) {
        Tag tag = repository.findById(tagId);
        return tag != null ?
                RestResponse.ok(tag.getTalks()) :
                RestResponse.notFound();
    }
}
