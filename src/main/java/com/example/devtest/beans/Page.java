package com.example.devtest.beans;


/**
 * A page implementation.
 * Contains:
 * <ul>
 *     <li>A list of items</li>
 *     <li>The page size</li>
 *     <li>The current page number (0 based)</li>
 *     <li>The total pages available (count)</li>
 *     <li>The total items in the dataset (i.e. inall pages, summed up)</li>
 * </ul>
 *
 * @param <T> The class of the items
 */
public class Page<T> {
    private final Iterable<T> items;
    private final Integer pageSize;
    private final Integer pageNumber;
    private final Integer totalPages;
    private final Long totalItems;

    /**
     * Constructs a page object
     *
     * @param items the list of items (current page only)
     * @param pageSize the page size (max number of items in a page, count)
     * @param pageNumber the current page number (0-based)
     * @param totalPages the total number of pages (count/length)
     * @param totalItems the total items in the whole dataset (not just the current page)
     */
    public Page(Iterable<T> items, Integer pageSize, Integer pageNumber, Integer totalPages, Long totalItems) {
        this.items = items;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
    }

    /**
     * Gets the list of current page items
     *
     * @return The current page items
     */
    public Iterable<T> getItems() {
        return items;
    }

    /**
     * Get the current page number
     *
     * @return The current page number (0-based)
     */
    public Integer getPageNumber() {
        return pageNumber;
    }

    /**
     * Get the total number of pages (count).
     *
     * Note that being a count, if it's 4 it means you can request page number 0, 1, 2, 3
     *
     * @return The total pages count
     */
    public Integer getTotalPages() {
        return totalPages;
    }

    /**
     * Get the defined page size, i.e. the max number of items per page
     *
     * @return The page standard size
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * Get the total number of items in the whole dataset, i.e. in all pages.
     *
     * It's basically the unpaged count.
     *
     * @return The total number of items in the dataset
     */
    public Long getTotalItems() {
        return totalItems;
    }
}
