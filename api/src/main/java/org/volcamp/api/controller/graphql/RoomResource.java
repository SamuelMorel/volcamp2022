package org.volcamp.api.controller.graphql;

import io.smallrye.graphql.api.Nullable;
import org.eclipse.microprofile.graphql.*;
import org.volcamp.api.entity.Room;
import org.volcamp.api.repository.RoomRepository;
import org.volcamp.api.utils.Pageable;
import org.volcamp.api.utils.token.roles.Roles;
import org.volcamp.api.utils.token.roles.RolesEnum;

import javax.inject.Inject;
import javax.transaction.Transactional;

@GraphQLApi
public class RoomResource {
    private final RoomRepository roomRepository;

    @Inject
    public RoomResource(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Query
    @Roles(RolesEnum.READ_ONLY)
    public Pageable<Room> findRooms(
            Room room,
            @DefaultValue("0") int index,
            @DefaultValue("10") int size,
            @Nullable String sortColumn,
            @DefaultValue("true") boolean asc) {
        return roomRepository.findList(room, index, size, sortColumn, asc);
    }

    @Query
    @Roles(RolesEnum.READ_ONLY)
    public Room findRoom(@NonNull Room room) {
        return roomRepository.findOne(room);
    }

    @Mutation
    @Transactional
    @Roles(RolesEnum.READ_WRITE)
    public Room createRoom(@NonNull Room room) {
        roomRepository.save(room);
        return room;
    }
}
