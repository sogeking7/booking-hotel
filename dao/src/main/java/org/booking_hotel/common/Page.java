package org.booking_hotel.common;

import java.util.List;

public class Page<T> {
    private final List<T> content;
    private final int totalPages;
    private final long totalElements;
    private final int page;
    private final int size;

    public Page(List<T> content, long totalElements, PageRequest pageRequest) {
        this.content = content;
        this.totalElements = totalElements;
        this.page = pageRequest.getPage();
        this.size = pageRequest.getSize();
        this.totalPages = (int) Math.ceil((double) totalElements / size);
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
        return page;
    }

    public int getSize() {
        return size;
    }

    public boolean hasNext() {
        return page < totalPages - 1;
    }

    public boolean hasPrevious() {
        return page > 0;
    }
}