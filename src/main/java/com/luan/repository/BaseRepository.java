package com.luan.repository;

import com.luan.commons.pagination.Pageable;
import com.luan.commons.pagination.Pagination;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

public abstract class BaseRepository<T> implements PanacheRepositoryBase<T, Long> {

    public Pagination buildPagination(Pageable pageable) {
        long count = count();
        long totalPages = count / pageable.getSize();
        return new Pagination(pageable.getPage(), totalPages, count);
    }
}
