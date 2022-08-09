package org.volcamp.api.repository;

import org.volcamp.api.entity.ConferenceType;
import org.volcamp.api.finder.ConferenceTypeFinder;
import org.volcamp.api.repository.generic.AbstractRepository;
import org.volcamp.api.utils.BeanUtils;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConferenceTypeRepository extends AbstractRepository<ConferenceType> {
    protected ConferenceTypeRepository() {
        super(ConferenceTypeFinder::new);
    }

    @Override
    protected void saveChildEntity(ConferenceType conferenceType) {
        conferenceType.setSessions(BeanUtils.injectBean(SessionRepository.class).save(conferenceType.getSessions()));
    }
}
