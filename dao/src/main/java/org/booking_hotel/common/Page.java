package org.booking_hotel.common;

import java.util.List;

public class Page<T> {
    private final List<T> content;
    private final int totalPages;
    private final long totalElements;
    private final int currentPage;
    private final int pageSize;

    public Page(List<T> content, long totalElements, PageRequest pageRequest) {
        this.content = content;
        this.totalElements = totalElements;
        this.currentPage = pageRequest.getPage();
        this.pageSize = pageRequest.getSize();
        this.totalPages = (int) Math.ceil((double) totalElements / pageSize);
    }

    public List<T> getContent() {
        return content;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public boolean hasNext() {
        return currentPage < totalPages - 1;
    }

    public boolean hasPrevious() {
        return currentPage > 0;
    }
}