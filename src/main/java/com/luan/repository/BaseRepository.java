package com.luan.service.repository;

import com.luan.pagination.Pagination;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import java.awt.print.Pageable;

public abstract class BaseRepository<T> implements PanacheRepositoryBase<T, Long> {

    public Pagination buildPagination(Pageable pageable) {
        long count = count();
        long totalPages = count / pageable.getSize();
        return new Pagination(pageable.getPage(), totalPages, count);
    }
}
