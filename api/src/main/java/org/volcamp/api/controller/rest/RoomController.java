package org.volcamp.api.controller.rest;

import org.jboss.resteasy.reactive.RestResponse;
import org.volcamp.api.controller.rest.generic.AbstractRestController;
import org.volcamp.api.entity.Room;
import org.volcamp.api.entity.Session;
import org.volcamp.api.repository.RoomRepository;
import org.volcamp.api.utils.Pageable;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("rooms")
public class RoomController extends AbstractRestController<Room> {
    @Inject
    public RoomController(RoomRepository roomRepository) {
        super(roomRepository);
    }

    @GET
    public RestResponse<Pageable<Room>> findList(
            @BeanParam Room entity,
            @QueryParam("index") @DefaultValue("0") Integer index,
            @QueryParam("size") @DefaultValue("10") Integer size,
            @QueryParam("sort") @DefaultValue("") String sortColumn,
            @QueryParam("asc") @DefaultValue("true") Boolean ascending) {
        return RestResponse.ok(repository.findList(entity, index, size, sortColumn, ascending));
    }

    @GET
    @Path("{id}/sessions")
    public RestResponse<List<Session>> tagTalks(@PathParam("id") UUID roomId) {
        Room room = repository.findById(roomId);
        return room != null ?
                RestResponse.ok(room.getSessions()) :
                RestResponse.notFound();
    }
}
