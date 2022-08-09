package org.volcamp.api.repository;

import org.volcamp.api.entity.Talk;
import org.volcamp.api.finder.TalkFinder;
import org.volcamp.api.repository.generic.AbstractRepository;
import org.volcamp.api.utils.BeanUtils;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TalkRepository extends AbstractRepository<Talk> {
    protected TalkRepository() {
        super(TalkFinder::new);
    }

    @Override
    protected void saveChildEntity(Talk talk) {
        talk.setAuthors(BeanUtils.injectBean(AuthorRepository.class).save(talk.getAuthors()));
        talk.setTags(BeanUtils.injectBean(TagRepository.class).save(talk.getTags()));
        talk.setSessions(BeanUtils.injectBean(SessionRepository.class).save(talk.getSessions()));
    }
}
