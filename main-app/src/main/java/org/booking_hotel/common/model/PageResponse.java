package org.booking_hotel.common.model;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.common.Page;

import java.util.List;

public record PageResponse<T>(
        @NotNull List<T> content,
        @NotNull int totalPages,
        @NotNull long totalElements,
        @NotNull int currentPage,
        @NotNull int pageSize,
        @NotNull boolean hasNext,
        @NotNull boolean hasPrevious
) {
    public static <T> PageResponse<T> of(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getCurrentPage(),
                page.getSize(),
                page.hasNext(),
                page.hasPrevious()
        );
    }
}