package org.booking_hotel.daos.countries.dto;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.jooq.model.tables.records.CountryRecord;

import java.time.OffsetDateTime;

public record CountryDto(
        @NotNull Long id,
        @NotNull OffsetDateTime createdAt,
        @NotNull OffsetDateTime updatedAt,
        @NotNull Boolean removed,

        @NotNull String name,
        @NotNull String code,
        @NotNull String currency
) {

    public static CountryDto of(CountryRecord record) {
        return new CountryDto(
                record.getId(),
                record.getCreatedAt(),
                record.getUpdatedAt(),
                record.getRemoved(),

                record.getName(),
                record.getCode(),
                record.getCurrency()
        );
    }
}
