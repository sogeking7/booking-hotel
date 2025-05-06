package org.booking_hotel.room_types.model;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.daos.room_types.dto.RoomTypeDto;

import java.time.OffsetDateTime;

public record RoomTypeModel(
        @NotNull Long id,
        @NotNull String name,
        @NotNull Long hotelId,
        @NotNull Long bedTypeId,
        @NotNull Integer count,

        @NotNull OffsetDateTime createdAt,
        @NotNull OffsetDateTime updatedAt,
        @NotNull Boolean removed
) {
    public static RoomTypeModel of(RoomTypeDto roomTypeDto) {
        return new RoomTypeModel(
                roomTypeDto.id(),
                roomTypeDto.name(),
                roomTypeDto.hotelId(),
                roomTypeDto.bedTypeId(),
                roomTypeDto.count(),

                roomTypeDto.createdAt(),
                roomTypeDto.updatedAt(),
                roomTypeDto.removed()
        );
    }
}