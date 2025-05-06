package org.booking_hotel.hotels.model;

import jakarta.validation.constraints.NotNull;

public record HotelSaveResponse(
        @NotNull Long id,
        @NotNull String name,
        @NotNull String address,
        @NotNull String phone,
        Long cityId
) {
}