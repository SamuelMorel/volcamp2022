package org.volcamp.api.controller.graphql;

import io.smallrye.graphql.api.Nullable;
import org.eclipse.microprofile.graphql.*;
import org.volcamp.api.entity.Session;
import org.volcamp.api.repository.SessionRepository;
import org.volcamp.api.utils.Pageable;
import org.volcamp.api.utils.token.roles.Roles;
import org.volcamp.api.utils.token.roles.RolesEnum;

import javax.inject.Inject;
import javax.transaction.Transactional;

@GraphQLApi
public class SessionResource {
    final SessionRepository sessionRepository;

    @Inject
    public SessionResource(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Query
    @Roles(RolesEnum.READ_ONLY)
    public Pageable<Session> findSessions(
            Session session,
            @DefaultValue("0") int index,
            @DefaultValue("10") int size,
            @Nullable String sortColumn,
            @DefaultValue("true") boolean asc) {
        return sessionRepository.findList(session, index, size, sortColumn, asc);
    }

    @Query
    @Roles(RolesEnum.READ_ONLY)
    public Session findSession(@NonNull Session session) {
        return sessionRepository.findOne(session);
    }

    @Mutation
    @Transactional
    @Roles(RolesEnum.READ_WRITE)
    public Session createSession(@NonNull Session session) {
        sessionRepository.save(session);
        return session;
    }
}
