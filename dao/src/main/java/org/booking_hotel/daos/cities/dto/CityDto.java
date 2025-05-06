package org.booking_hotel.daos.cities.dto;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.jooq.model.tables.records.CityRecord;

import java.time.OffsetDateTime;

public record CityDto(
        @NotNull Long id,
        @NotNull OffsetDateTime createdAt,
        @NotNull OffsetDateTime updatedAt,
        @NotNull Boolean removed,

        @NotNull String name,
        Long countryId
) {
    public static CityDto of(CityRecord record) {
        return new CityDto(
                record.getId(),
                record.getCreatedAt(),
                record.getUpdatedAt(),
                record.getRemoved(),

                record.getName(),
                record.getCountryId()
        );
    }
}
