package org.volcamp.api.finder;

import org.volcamp.api.entity.ConferenceType;
import org.volcamp.api.finder.generic.AbstractFinder;

import static org.volcamp.api.entity.QConferenceType.conferenceType;

public class ConferenceTypeFinder extends AbstractFinder<ConferenceType> {
    public ConferenceTypeFinder() {
        super(conferenceType);
    }

    @Override
    protected void filter(ConferenceType filter) {
        addClause(conferenceType.id, filter.getId());
        addClause(conferenceType.name, filter.getName());

        new SessionFinder().filterForOtherEntity(
                this,
                conferenceType.sessions,
                filter.getSessions()
        );
    }
}
