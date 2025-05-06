package org.booking_hotel.room_type_facilities.model;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.room_type_facilities.dto.RoomTypeFacilityDto;

import java.time.OffsetDateTime;

public record RoomTypeFacilityModel(
        @NotNull Long id,
        @NotNull Long roomTypeId,
        @NotNull Long facilityId,

        @NotNull OffsetDateTime createdAt,
        @NotNull OffsetDateTime updatedAt,
        @NotNull Boolean removed

) {

    public static RoomTypeFacilityModel of(RoomTypeFacilityDto roomTypeFacilityDto) {
        return new RoomTypeFacilityModel(
                roomTypeFacilityDto.id(),
                roomTypeFacilityDto.roomTypeId(),
                roomTypeFacilityDto.facilityId(),

                roomTypeFacilityDto.createdAt(),
                roomTypeFacilityDto.updatedAt(),
                roomTypeFacilityDto.removed()
        );
    }
}