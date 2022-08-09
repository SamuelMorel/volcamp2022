package org.volcamp.api.controller.graphql;

import io.smallrye.graphql.api.Nullable;
import org.eclipse.microprofile.graphql.*;
import org.volcamp.api.entity.Author;
import org.volcamp.api.repository.AuthorRepository;
import org.volcamp.api.utils.Pageable;
import org.volcamp.api.utils.token.roles.Roles;
import org.volcamp.api.utils.token.roles.RolesEnum;

import javax.inject.Inject;
import javax.transaction.Transactional;

@GraphQLApi
public class AuthorResource {
    private final AuthorRepository authorRepository;

    @Inject
    public AuthorResource(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Query
    @Roles(RolesEnum.READ_ONLY)
    public Pageable<Author> findAuthors(
            Author author,
            @DefaultValue("0") int index,
            @DefaultValue("10") int size,
            @Nullable String sortColumn,
            @DefaultValue("true") boolean asc) {
        return authorRepository.findList(author, index, size, sortColumn, asc);
    }

    @Query
    @Roles(RolesEnum.READ_ONLY)
    public Author findAuthor(@NonNull Author author) {
        return authorRepository.findOne(author);
    }

    @Mutation
    @Transactional
    @Roles(RolesEnum.READ_WRITE)
    public Author createAuthor(@NonNull Author author) {
        authorRepository.save(author);
        return author;
    }
}
