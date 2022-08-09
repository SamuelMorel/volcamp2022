package org.volcamp.api.finder;

import org.volcamp.api.entity.Room;
import org.volcamp.api.finder.generic.AbstractFinder;

import static org.volcamp.api.entity.QRoom.room;

public class RoomFinder extends AbstractFinder<Room> {
    public RoomFinder() {
        super(room);
    }

    @Override
    public void filter(Room filter) {
        addClause(room.id, filter.getId());
        addClause(room.name, filter.getName());

        new SessionFinder().filterForOtherEntity(
                this,
                room.sessions,
                filter.getSessions()
        );
    }
}
