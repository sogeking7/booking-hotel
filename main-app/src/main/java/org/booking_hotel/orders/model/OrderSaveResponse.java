package org.booking_hotel.orders.model;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record OrderSaveResponse(
        @NotNull Long id,
        @NotNull LocalDate fromDate,
        @NotNull LocalDate toData,
        @NotNull Long hotelId,
        @NotNull Long userId,
        @NotNull Long roomTypeId
) {
}
