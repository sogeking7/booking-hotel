package org.booking_hotel.daos.room_types.dto;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.jooq.model.tables.records.RoomTypeRecord;

import java.time.OffsetDateTime;

public record RoomTypeDto(
        @NotNull Long id,
        @NotNull OffsetDateTime createdAt,
        @NotNull OffsetDateTime updatedAt,
        @NotNull Boolean removed,

        @NotNull Long hotelId,
        @NotNull Long bedTypeId,
        @NotNull String name,
        @NotNull Integer count
) {
    public static RoomTypeDto of(RoomTypeRecord record) {
        return new RoomTypeDto(
                record.getId(),
                record.getCreatedAt(),
                record.getUpdatedAt(),
                record.getRemoved(),

                record.getHotelId(),
                record.getBedTypeId(),
                record.getName(),
                record.getCount()
        );
    }
}