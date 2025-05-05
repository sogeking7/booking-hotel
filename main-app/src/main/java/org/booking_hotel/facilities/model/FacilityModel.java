package org.booking_hotel.facilities.model;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.facilities.dto.FacilityDto;
import org.booking_hotel.jooq.model.enums.FacilityTypes;

import java.time.OffsetDateTime;

public record FacilityModel(
        @NotNull Long id,
        @NotNull String name,
        @NotNull String iconRef,
        FacilityTypes type,

        @NotNull OffsetDateTime createdAt,
        @NotNull OffsetDateTime updatedAt,
        @NotNull Boolean removed

) {

    public static FacilityModel of(FacilityDto facilityDto) {
        return new FacilityModel(
                facilityDto.id(),
                facilityDto.name(),
                facilityDto.iconRef(),
                facilityDto.type(),

                facilityDto.createdAt(),
                facilityDto.updatedAt(),
                facilityDto.removed()
        );
    }
}