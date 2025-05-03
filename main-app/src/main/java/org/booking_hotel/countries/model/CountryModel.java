package org.booking_hotel.countries.model;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.countries.dto.CountryDto;

import java.time.OffsetDateTime;

public record CountryModel(
        @NotNull Long id,
        @NotNull String name,
        @NotNull String currency,
        @NotNull String code,
        @NotNull OffsetDateTime createdAt,
        @NotNull OffsetDateTime updatedAt,
        @NotNull Boolean removed

) {

    public static CountryModel of(CountryDto countryDto) {
        return new CountryModel(
                countryDto.id(),
                countryDto.name(),
                countryDto.currency(),
                countryDto.code(),
                countryDto.createdAt(),
                countryDto.updatedAt(),
                countryDto.removed()
        );
    }
}