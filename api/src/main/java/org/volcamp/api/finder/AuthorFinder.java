package org.volcamp.api.finder;

import org.volcamp.api.entity.Author;
import org.volcamp.api.finder.generic.AbstractFinder;

import static org.volcamp.api.entity.QAuthor.author;

public class AuthorFinder extends AbstractFinder<Author> {
    public AuthorFinder() {
        super(author);
    }

    @Override
    protected void filter(Author filter) {
        addClause(author.id, filter.getId());
        addClause(author.firstName, filter.getFirstName());
        addClause(author.lastName, filter.getLastName());

        new TalkFinder().filterForOtherEntity(
                this,
                author.talks,
                filter.getTalks()
        );
    }
}
