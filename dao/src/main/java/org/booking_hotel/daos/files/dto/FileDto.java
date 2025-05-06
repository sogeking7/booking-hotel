package org.booking_hotel.daos.files.dto;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.jooq.model.tables.records.FileRecord;

import java.time.OffsetDateTime;

public record FileDto(
        @NotNull Long id,
        @NotNull OffsetDateTime createdAt,
        @NotNull OffsetDateTime updatedAt,
        @NotNull Boolean removed,

        @NotNull String name,
        @NotNull String urlPath,
        @NotNull String type,
        @NotNull Long size
) {
    public static FileDto of(FileRecord record) {
        return new FileDto(
                record.getId(),
                record.getCreatedAt(),
                record.getUpdatedAt(),
                record.getRemoved(),

                record.getName(),
                record.getUrlPath(),
                record.getType(),
                record.getSize()
        );
    }
}