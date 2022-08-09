package org.volcamp.api.finder.generic;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.Getter;
import org.volcamp.api.entity.generic.AbstractEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The class aims to provide find filters for an entity.
 * Based on a filter, build a JPA query
 *
 * @param <Entity> the entity to filter on
 */
public abstract class AbstractFinder<Entity extends AbstractEntity> {
    private final EntityPath<Entity> finderBaseEntity;
    private JPAQuery<?> query;
    @Getter
    private String queryString;

    private List<Predicate> queryPredicates = new ArrayList<>();
    private final List<Object> queryParameters = new ArrayList<>();

    public Object[] getParameters() {
        return queryParameters.toArray(Object[]::new);
    }

    /**
     * The finder constructor.
     *
     * @param dslEntity the Query DSL generated entity base on {@code Entity}
     */
    protected AbstractFinder(EntityPathBase<Entity> dslEntity) {
        query = new JPAQuery<>()
                .select(dslEntity)
                .from(dslEntity);

        finderBaseEntity = dslEntity;
    }

    /**
     * The filter method will be called with an {@code Entity}
     *
     * @param filter the object to take as source in order to build predicates
     */
    protected abstract void filter(Entity filter);

    /**
     * Method to call in order to build the query string from an entity as filter
     *
     * @param filter the filter to translate in predicates
     * @return self
     */
    public AbstractFinder<Entity> build(Entity filter) {
        if (filter != null) {
            this.filter(filter);
        }

        // store the query string output in a variable to be able to call twice the build method
        // without duplicate the predicate list
        if (queryString == null) {
            queryString = queryPredicates.isEmpty() ? query.toString() : query.where(queryPredicates.toArray(Predicate[]::new)).toString();
        }

        return this;
    }

    /**
     * This method aims to filter on another entity (child or parent)
     *
     * @param sourceFinder the finder that called this method
     * @param path         the path from the source entity to this entity
     * @param filter       the filter for this entity
     */
    public void filterForOtherEntity(
            AbstractFinder<?> sourceFinder,
            EntityPath<Entity> path,
            Entity filter
    ) {
        this.query = sourceFinder.query;

        if (filter == null) {
            return;
        }

        query.join(path, finderBaseEntity);

        this.filter(filter);
        this.propagate(sourceFinder);
    }

    /**
     * This method aims to filter on another entity List (child or parent)
     *
     * @param sourceFinder the finder that called this method
     * @param path         the path from the source entity to this entity
     * @param filters      the filters for this entity
     */
    public void filterForOtherEntity(
            AbstractFinder<?> sourceFinder,
            ListPath<Entity, ? extends EntityPath<Entity>> path,
            List<Entity> filters
    ) {
        this.query = sourceFinder.query;

        if (filters == null || filters.isEmpty()) {
            return;
        }

        query.join(path, finderBaseEntity);

        List<Predicate> orPredicate = new ArrayList<>();
        filters.forEach(filter -> {
            this.filter(filter);
            orPredicate.addAll(queryPredicates);
            queryPredicates = new ArrayList<>();
        });

        queryPredicates.add(orPredicate.stream().reduce((left, right) -> ((BooleanExpression) left).and(right)).orElse(null));

        this.propagate(sourceFinder);
    }

    /**
     * Propagate the built query parameters and predicates to source finder
     *
     * @param sourceFinder the source finder that will run the query
     */
    protected void propagate(AbstractFinder<?> sourceFinder) {
        if (!queryPredicates.isEmpty()) {
            sourceFinder.queryParameters.addAll(queryParameters);
            sourceFinder.queryPredicates.addAll(queryPredicates);
        }
    }

    /**
     * Add a custom build predicate to the list of condition that will be applied
     *
     * @param predicate the build predicate
     * @param parameter the parameter on which the predicate hold
     */
    protected void addPredicate(Predicate predicate, Object parameter) {
        queryPredicates.add(predicate);
        queryParameters.add(parameter);
    }

    protected void addPredicate(Predicate predicate) {

    }

    /**
     * Generic equals clause
     *
     * @param path  the path to the field
     * @param value the value of the field
     * @param <P>   the type of the field
     */
    protected <P> void addClause(SimpleExpression<P> path, P value) {
        if (value != null) {
            addPredicate(path.eq(value), value);
        }
    }

    protected void addClause(StringPath path, String value) {
        if (value != null) {
            addPredicate(path.contains(value), value);
        }
    }

    protected void addClause(DateTimePath<LocalDateTime> path, LocalDateTime value) {
        addClause(path, value, null);
    }

    protected void addClause(DateTimePath<LocalDateTime> path, LocalDateTime value, Boolean greater) {
        if (value == null) {
            return;
        }

        if (greater != null) {
            addPredicate(greater ? path.goe(value) : path.loe(value), value);
            return;
        }

        addPredicate(path.year().eq(value.getYear()), value.getYear());
        addPredicate(path.month().eq(value.getMonthValue()), value.getMonthValue());
        addPredicate(path.dayOfMonth().eq(value.getDayOfMonth()), value.getDayOfMonth());

        if (value.getHour() != 0) {
            addPredicate(path.hour().eq(value.getHour()), value.getHour());
        }
        if (value.getMinute() != 0) {
            addPredicate(path.minute().eq(value.getMinute()), value.getMinute());
        }
        if (value.getSecond() != 0) {
            addPredicate(path.second().eq(value.getSecond()), value.getSecond());
        }
        if (value.getNano() != 0) {
            addPredicate(path.milliSecond().eq(value.getNano() / 1_000_000), value.getNano() / 1_000_000);
        }
    }
}
