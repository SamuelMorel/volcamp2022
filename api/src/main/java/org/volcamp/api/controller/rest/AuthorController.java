package org.volcamp.api.controller.rest;

import org.jboss.resteasy.reactive.RestResponse;
import org.volcamp.api.controller.rest.generic.AbstractRestController;
import org.volcamp.api.entity.Author;
import org.volcamp.api.entity.Talk;
import org.volcamp.api.repository.AuthorRepository;
import org.volcamp.api.utils.Pageable;
import org.volcamp.api.utils.token.roles.Roles;
import org.volcamp.api.utils.token.roles.RolesEnum;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("authors")
public class AuthorController extends AbstractRestController<Author> {
    @Inject
    public AuthorController(AuthorRepository authorRepository) {
        super(authorRepository);
    }

    @GET
    public RestResponse<Pageable<Author>> findList(
            @BeanParam Author entity,
            @QueryParam("index") @DefaultValue("0") Integer index,
            @QueryParam("size") @DefaultValue("10") Integer size,
            @QueryParam("sort") @DefaultValue("") String sortColumn,
            @QueryParam("asc") @DefaultValue("true") Boolean ascending) {
        return RestResponse.ok(repository.findList(entity, index, size, sortColumn, ascending));
    }

    @GET
    @Path("{id}/talks")
    @Roles(RolesEnum.READ_ONLY)
    public RestResponse<List<Talk>> tagTalks(@PathParam("id") UUID authorId) {
        Author author = repository.findById(authorId);
        return author != null ?
                RestResponse.ok(author.getTalks()) :
                RestResponse.notFound();
    }
}
