package org.volcamp.api.finder;

import org.volcamp.api.entity.Talk;
import org.volcamp.api.finder.generic.AbstractFinder;

import static org.volcamp.api.entity.QTalk.talk;

public class TalkFinder extends AbstractFinder<Talk> {
    public TalkFinder() {
        super(talk);
    }

    @Override
    protected void filter(Talk filter) {
        addClause(talk.id, filter.getId());
        addClause(talk.title, filter.getTitle());

        new AuthorFinder().filterForOtherEntity(
                this,
                talk.authors,
                filter.getAuthors()
        );

        new TagFinder().filterForOtherEntity(
                this,
                talk.tags,
                filter.getTags()
        );

        new SessionFinder().filterForOtherEntity(
                this,
                talk.sessions,
                filter.getSessions()
        );
    }
}
