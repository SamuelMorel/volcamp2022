package org.volcamp.api.utils;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class Pageable<T> {
    private Long total = 0L;
    private int size = 10;
    private int index = 0;
    private List<T> content = new ArrayList<>();

    public Pageable(PanacheQuery<T> query, Page page) {
        this.total = query.count();
        size = page.size;
        index = page.index;
        content = query.page(index, size).list();
    }
}
