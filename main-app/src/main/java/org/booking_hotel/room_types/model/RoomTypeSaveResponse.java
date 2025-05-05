package org.booking_hotel.room_types.model;

import jakarta.validation.constraints.NotNull;

public record RoomTypeSaveResponse(@NotNull Long id) {
}