package org.volcamp.api.repository;

import org.volcamp.api.entity.Room;
import org.volcamp.api.finder.RoomFinder;
import org.volcamp.api.repository.generic.AbstractRepository;
import org.volcamp.api.utils.BeanUtils;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoomRepository extends AbstractRepository<Room> {
    protected RoomRepository() {
        super(RoomFinder::new);
    }

    @Override
    protected void saveChildEntity(Room room) {
        room.setSessions(BeanUtils.injectBean(SessionRepository.class).save(room.getSessions()));
    }
}
