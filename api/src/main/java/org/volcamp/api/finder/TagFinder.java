package org.volcamp.api.finder;

import org.volcamp.api.entity.Tag;
import org.volcamp.api.finder.generic.AbstractFinder;

import static org.volcamp.api.entity.QTag.tag;

public class TagFinder extends AbstractFinder<Tag> {
    public TagFinder() {
        super(tag);
    }

    @Override
    protected void filter(Tag filter) {
        addClause(tag.id, filter.getId());
        addClause(tag.value, filter.getValue());

        new TalkFinder().filterForOtherEntity(
                this,
                tag.talks,
                filter.getTalks()
        );
    }
}
