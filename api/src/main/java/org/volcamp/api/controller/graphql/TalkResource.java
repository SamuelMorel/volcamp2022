package org.volcamp.api.controller.graphql;

import io.smallrye.graphql.api.Nullable;
import org.eclipse.microprofile.graphql.*;
import org.volcamp.api.entity.Talk;
import org.volcamp.api.repository.TalkRepository;
import org.volcamp.api.utils.Pageable;
import org.volcamp.api.utils.token.roles.Roles;
import org.volcamp.api.utils.token.roles.RolesEnum;

import javax.inject.Inject;
import javax.transaction.Transactional;

@GraphQLApi
public class TalkResource {
    private final TalkRepository talkRepository;

    @Inject
    public TalkResource(TalkRepository talkRepository) {
        this.talkRepository = talkRepository;
    }

    @Query
    @Roles(RolesEnum.READ_ONLY)
    public Pageable<Talk> findTalks(
            Talk talk,
            @DefaultValue("0") int index,
            @DefaultValue("10") int size,
            @Nullable String sortColumn,
            @DefaultValue("true") boolean asc) {
        return talkRepository.findList(talk, index, size, sortColumn, asc);
    }

    @Query
    @Roles(RolesEnum.READ_ONLY)
    public Talk findTalk(@NonNull Talk talk) {
        return talkRepository.findOne(talk);
    }

    @Mutation
    @Transactional
    @Roles(RolesEnum.READ_WRITE)
    public Talk createTalk(Talk talk) {
        return talkRepository.save(talk);
    }
}
