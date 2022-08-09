package org.volcamp.api.repository.generic;

import com.querydsl.core.NonUniqueResultException;
import com.querydsl.core.util.StringUtils;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.JDBCException;
import org.volcamp.api.entity.generic.AbstractEntity;
import org.volcamp.api.exception.BadRequestException;
import org.volcamp.api.exception.MultipleResultsFound;
import org.volcamp.api.finder.generic.AbstractFinder;
import org.volcamp.api.utils.Pageable;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * This class set up some CRUD operations for an entity
 *
 * @param <Entity> the entity to use in DB. The entity must extend {@code AbstractEntity} to be sure
 *                 that ID is managed and of UUID type
 */
@Slf4j
public abstract class AbstractRepository<Entity extends AbstractEntity> implements PanacheRepositoryBase<Entity, UUID> {
    private final Supplier<? extends AbstractFinder<Entity>> constructor;

    protected AbstractRepository(Supplier<? extends AbstractFinder<Entity>> constructor) {
        this.constructor = constructor;
    }

    public List<Entity> save(List<Entity> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(this::save).toList();
    }

    public Entity save(Entity entity) {
        if (entity == null) {
            return null;
        }

        // search if entity exists in db
        Entity inDbEntity = findOne(entity);
        if (inDbEntity != null) {
            return inDbEntity;
        }

        // save child entities if needed
        this.saveChildEntity(entity);

        // save the entity
        try {
            persist(entity);
        } catch (PersistenceException persistenceException) {
            if (persistenceException.getCause() instanceof JDBCException jdbcException) {
                throw new BadRequestException(jdbcException.getSQLException().getMessage());
            }
            throw persistenceException;
        }

        return entity;
    }

    protected abstract void saveChildEntity(Entity entity);

    public Pageable<Entity> findList(Entity filter, int index, int size, String sortColumn, Boolean asc) {
        try {
            return new Pageable<>(buildPanacheQuery(filter, sort(sortColumn, asc)), Page.of(index, size));
        } catch (NoResultException noResultException) {
            log.debug("No result found");
            return new Pageable<>();
        } catch (PersistenceException persistenceException) {
            if (persistenceException.getCause() instanceof JDBCException jdbcException) {
                throw new BadRequestException(jdbcException.getSQLException().getMessage());
            }
            throw persistenceException;
        }
    }

    public Entity findOne(Entity filter) {
        try {
            return buildPanacheQuery(filter, Sort.empty()).singleResult();
        } catch (NonUniqueResultException nonUniqueResultException) {
            throw new MultipleResultsFound();
        } catch (NoResultException noResultException) {
            log.debug("No result found");
            return null;
        } catch (PersistenceException persistenceException) {
            if (persistenceException.getCause() instanceof JDBCException jdbcException) {
                throw new BadRequestException(jdbcException.getSQLException().getMessage());
            }
            throw persistenceException;
        }
    }

    private PanacheQuery<Entity> buildPanacheQuery(Entity filter, Sort sort) {
        AbstractFinder<Entity> finder = this.constructor.get().build(filter);

        return find(finder.getQueryString(), sort, finder.getParameters());
    }

    public boolean delete(UUID id) {
        return deleteById(id);
    }

    protected Sort sort(String sortColumn, Boolean ascending) {
        Sort.Direction direction = ascending ? Sort.Direction.Ascending : Sort.Direction.Descending;
        if (StringUtils.isNullOrEmpty(sortColumn)) {
            return Sort.by("id", direction);
        }

        return Sort.by(sortColumn, direction);
    }

}
