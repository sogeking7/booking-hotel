package org.booking_hotel.bed_types.dto;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.jooq.model.tables.records.BedTypeRecord;

import java.time.OffsetDateTime;

public record BedTypeDto(
        @NotNull Long id,
        @NotNull OffsetDateTime createdAt,
        @NotNull OffsetDateTime updatedAt,
        @NotNull Boolean removed,

        @NotNull String name,
        @NotNull String iconRef
) {
    public static BedTypeDto of(BedTypeRecord record) {
        return new BedTypeDto(
                record.getId(),
                record.getCreatedAt(),
                record.getUpdatedAt(),
                record.getRemoved(),

                record.getName(),
                record.getIconRef()
        );
    }
}