package org.booking_hotel.room_types.model;

import jakarta.validation.constraints.NotNull;

public record RoomTypeSaveRequest(
        Long id,
        @NotNull String name,
        @NotNull Long hotelId,
        @NotNull Long bedTypeId,
        @NotNull Integer count
) {
}