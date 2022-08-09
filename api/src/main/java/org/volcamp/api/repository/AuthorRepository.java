package org.volcamp.api.repository;

import org.volcamp.api.entity.Author;
import org.volcamp.api.finder.AuthorFinder;
import org.volcamp.api.repository.generic.AbstractRepository;
import org.volcamp.api.utils.BeanUtils;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthorRepository extends AbstractRepository<Author> {
    protected AuthorRepository() {
        super(AuthorFinder::new);
    }

    @Override
    protected void saveChildEntity(Author author) {
        author.setTalks(BeanUtils.injectBean(TalkRepository.class).save(author.getTalks()));
    }
}
