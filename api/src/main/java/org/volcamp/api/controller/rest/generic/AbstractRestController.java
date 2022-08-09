package org.volcamp.api.controller.rest.generic;

import org.jboss.resteasy.reactive.RestResponse;
import org.volcamp.api.entity.generic.AbstractEntity;
import org.volcamp.api.repository.generic.AbstractRepository;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AbstractRestController<Entity extends AbstractEntity> {
    protected final AbstractRepository<Entity> repository;

    public AbstractRestController(AbstractRepository<Entity> repository) {
        this.repository = repository;
    }

    @GET
    @Path("/{id}")
    public RestResponse<Entity> findOne(@PathParam("id") UUID id) {
        return RestResponse.ok(repository.findById(id));
    }

    @POST
    @Transactional
    public RestResponse<Entity> create(Entity entity) {
        return RestResponse.ok(repository.save(entity));
    }

    @DELETE
    @Transactional
    public RestResponse<Boolean> delete(UUID id) {
        return repository.delete(id) ? RestResponse.ok() : RestResponse.notFound();
    }
}
