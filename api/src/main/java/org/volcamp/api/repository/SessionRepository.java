package org.volcamp.api.repository;

import org.volcamp.api.entity.Session;
import org.volcamp.api.finder.SessionFinder;
import org.volcamp.api.repository.generic.AbstractRepository;
import org.volcamp.api.utils.BeanUtils;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SessionRepository extends AbstractRepository<Session> {
    protected SessionRepository() {
        super(SessionFinder::new);
    }

    @Override
    protected void saveChildEntity(Session session) {
        session.setRoom(BeanUtils.injectBean(RoomRepository.class).save(session.getRoom()));
        session.setTalk(BeanUtils.injectBean(TalkRepository.class).save(session.getTalk()));
        session.setConferenceType(BeanUtils.injectBean(ConferenceTypeRepository.class).save(session.getConferenceType()));
    }
}
