package org.booking_hotel.cities.model;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.cities.dto.CityDto;

import java.time.OffsetDateTime;

public record CityModel(
        @NotNull Long id,
        @NotNull String name,
        Long countryId,

        @NotNull OffsetDateTime createdAt,
        @NotNull OffsetDateTime updatedAt,
        @NotNull Boolean removed

) {

    public static CityModel of(CityDto cityDto) {
        return new CityModel(
                cityDto.id(),
                cityDto.name(),
                cityDto.countryId(),

                cityDto.createdAt(),
                cityDto.updatedAt(),
                cityDto.removed()
        );
    }
}