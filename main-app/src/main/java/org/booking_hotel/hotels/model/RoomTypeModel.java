package org.booking_hotel.hotels.model;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.daos.room_types.dto.RoomTypeDto;

import java.time.OffsetDateTime;
import java.util.List;

public record RoomTypeModel(
        @NotNull Long id,
        @NotNull Long hotelId,
        @NotNull Long bedTypeId,
        @NotNull String name,
        @NotNull Integer count,
        BedTypeModel bedType,
        List<FacilityModel> facilities,

        @NotNull OffsetDateTime createdAt,
        @NotNull OffsetDateTime updatedAt,
        @NotNull Boolean removed
) {
    public static RoomTypeModel of(RoomTypeDto roomTypeDto) {
        return new RoomTypeModel(
                roomTypeDto.id(),
                roomTypeDto.hotelId(),
                roomTypeDto.bedTypeId(),
                roomTypeDto.name(),
                roomTypeDto.count(),
                null,
                null,

                roomTypeDto.createdAt(),
                roomTypeDto.updatedAt(),
                roomTypeDto.removed()
        );
    }

    public static RoomTypeModel of(RoomTypeDto roomTypeDto, BedTypeModel bedType, List<FacilityModel> facilities) {
        return new RoomTypeModel(
                roomTypeDto.id(),
                roomTypeDto.hotelId(),
                roomTypeDto.bedTypeId(),
                roomTypeDto.name(),
                roomTypeDto.count(),
                bedType,
                facilities,

                roomTypeDto.createdAt(),
                roomTypeDto.updatedAt(),
                roomTypeDto.removed()
        );
    }
}