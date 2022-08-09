package org.volcamp.api.finder;

import org.volcamp.api.entity.Session;
import org.volcamp.api.exception.BadRequestException;
import org.volcamp.api.finder.generic.AbstractFinder;

import static org.volcamp.api.entity.QSession.session;

public class SessionFinder extends AbstractFinder<Session> {
    public SessionFinder() {
        super(session);
    }

    @Override
    protected void filter(Session filter) {
        // check filter coherence
        if (filter.getEndTime() != null && (filter.getEndTimeAfter() != null || filter.getEndTimeBefore() != null)) {
            throw new BadRequestException("Cannot filter on endTime and endTimeBefore or endTimeAfter");
        }

        if (filter.getStartTime() != null && (filter.getStartTimeAfter() != null || filter.getStartTimeBefore() != null)) {
            throw new BadRequestException("Cannot filter on startTime and startTimeBefore or startTimeAfter");
        }

        addClause(session.id, filter.getId());
        addClause(session.startTime, filter.getStartTime());
        addClause(session.startTime, filter.getStartTimeBefore(), false);
        addClause(session.startTime, filter.getStartTimeAfter(), true);
        addClause(session.endTime, filter.getEndTime());
        addClause(session.endTime, filter.getEndTimeBefore(), false);
        addClause(session.endTime, filter.getEndTimeAfter(), true);


        new TalkFinder().filterForOtherEntity(
                this,
                session.talk,
                filter.getTalk()
        );

        new ConferenceTypeFinder().filterForOtherEntity(
                this,
                session.conferenceType,
                filter.getConferenceType()
        );

        new RoomFinder().filterForOtherEntity(
                this,
                session.room,
                filter.getRoom()
        );
    }
}
