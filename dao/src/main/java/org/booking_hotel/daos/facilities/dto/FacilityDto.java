package org.booking_hotel.daos.facilities.dto;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.jooq.model.enums.FacilityTypes;
import org.booking_hotel.jooq.model.tables.records.FacilityRecord;

import java.time.OffsetDateTime;

public record FacilityDto(
        @NotNull Long id,
        @NotNull OffsetDateTime createdAt,
        @NotNull OffsetDateTime updatedAt,
        @NotNull Boolean removed,

        @NotNull String name,
        @NotNull String iconRef,
        FacilityTypes type
) {
    public static FacilityDto of(FacilityRecord record) {
        return new FacilityDto(
                record.getId(),
                record.getCreatedAt(),
                record.getUpdatedAt(),
                record.getRemoved(),

                record.getName(),
                record.getIconRef(),
                record.getType()
        );
    }
}