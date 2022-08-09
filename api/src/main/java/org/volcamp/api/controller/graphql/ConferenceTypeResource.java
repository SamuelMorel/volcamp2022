package org.volcamp.api.controller.graphql;

import io.smallrye.graphql.api.Nullable;
import org.eclipse.microprofile.graphql.*;
import org.volcamp.api.entity.ConferenceType;
import org.volcamp.api.repository.ConferenceTypeRepository;
import org.volcamp.api.utils.Pageable;
import org.volcamp.api.utils.token.roles.Roles;
import org.volcamp.api.utils.token.roles.RolesEnum;

import javax.inject.Inject;
import javax.transaction.Transactional;

@GraphQLApi
public class ConferenceTypeResource {
    private final ConferenceTypeRepository conferenceTypeRepository;

    @Inject
    public ConferenceTypeResource(ConferenceTypeRepository conferenceTypeRepository) {
        this.conferenceTypeRepository = conferenceTypeRepository;
    }

    @Query
    @Roles(RolesEnum.READ_ONLY)
    public Pageable<ConferenceType> findConferenceTypes(
            ConferenceType conferenceType,
            @DefaultValue("0") int index,
            @DefaultValue("10") int size,
            @Nullable String sortColumn,
            @DefaultValue("true") boolean asc) {
        return conferenceTypeRepository.findList(conferenceType, index, size, sortColumn, asc);
    }

    @Query
    @Roles(RolesEnum.READ_ONLY)
    public ConferenceType findConferenceType(@NonNull ConferenceType conferenceType) {
        return conferenceTypeRepository.findOne(conferenceType);
    }

    @Mutation
    @Transactional
    @Roles(RolesEnum.READ_WRITE)
    public ConferenceType createConferenceType(@NonNull ConferenceType conferenceType) {
        conferenceTypeRepository.save(conferenceType);
        return conferenceType;
    }
}
