package org.volcamp.api.repository;

import org.volcamp.api.entity.Tag;
import org.volcamp.api.finder.TagFinder;
import org.volcamp.api.repository.generic.AbstractRepository;
import org.volcamp.api.utils.BeanUtils;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TagRepository extends AbstractRepository<Tag> {
    protected TagRepository() {
        super(TagFinder::new);
    }

    @Override
    protected void saveChildEntity(Tag tag) {
        tag.setTalks(BeanUtils.injectBean(TalkRepository.class).save(tag.getTalks()));
    }
}
