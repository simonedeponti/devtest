package com.example.devtest.controllers;

import com.example.devtest.beans.Page;
import org.springframework.data.domain.Pageable;

/**
 * Utility class for controllers that need to return paged data (e.g. in listings)
 *
 * @param <T> The class of items the controller deals with
 */
public class AbstractPagedController<T> {
    /**
     * Get a page from a {@link org.springframework.data.domain.Page} page
     *
     * @param page Spring's data-jdbc OOTB Page object
     * @return The app-specific page
     */
    public Page<T> getPage(org.springframework.data.domain.Page<T> page) {
        return new Page<>(
                page.getContent(),
                page.getSize(),
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements());
    }

    /**
     * Get a page from base data
     *
     * @param items The items in the current page
     * @param count The total count of items in the dataset (i.e. the unpaged count)
     * @param pageRequest The page request for page number, size, offset
     * @return The app-specific page
     */
    public Page<T> getPage(Iterable<T> items, long count, Pageable pageRequest) {
        Integer totalPages = Math.toIntExact(count / pageRequest.getPageSize());
        if((count % pageRequest.getPageSize()) > 0)
            totalPages++;
        return new Page<>(items, pageRequest.getPageSize(), pageRequest.getPageNumber(), totalPages, count);
    }
}
