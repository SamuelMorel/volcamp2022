package org.volcamp.api.controller.graphql;

import io.smallrye.graphql.api.Nullable;
import org.eclipse.microprofile.graphql.*;
import org.volcamp.api.entity.Tag;
import org.volcamp.api.repository.TagRepository;
import org.volcamp.api.utils.Pageable;
import org.volcamp.api.utils.token.roles.Roles;
import org.volcamp.api.utils.token.roles.RolesEnum;

import javax.inject.Inject;
import javax.transaction.Transactional;

@GraphQLApi
public class TagResource {
    final TagRepository tagRepository;

    @Inject
    public TagResource(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Query
    @Roles(RolesEnum.READ_ONLY)
    public Pageable<Tag> findTags(
            Tag tag,
            @DefaultValue("0") int index,
            @DefaultValue("10") int size,
            @Nullable String sortColumn,
            @DefaultValue("true") boolean asc) {
        return tagRepository.findList(tag, index, size, sortColumn, asc);
    }

    @Query
    @Roles(RolesEnum.READ_ONLY)
    public Tag findTag(@NonNull Tag tag) {
        return tagRepository.findOne(tag);
    }

    @Mutation
    @Transactional
    @Roles(RolesEnum.READ_WRITE)
    public Tag createTag(@NonNull Tag tag) {
        tagRepository.save(tag);
        return tag;
    }
}
