package org.booking_hotel.orders.model;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.daos.orders.dto.OrderDto;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record OrderModel(
        @NotNull Long id,
        @NotNull LocalDate fromDate,
        @NotNull LocalDate toData,
        @NotNull Long hotelId,
        @NotNull Long userId,
        @NotNull Long roomTypeId,

        @NotNull OffsetDateTime createdAt,
        @NotNull OffsetDateTime updatedAt,
        @NotNull Boolean removed

) {

    public static OrderModel of(OrderDto orderDto) {
        return new OrderModel(
                orderDto.id(),
                orderDto.fromDate(),
                orderDto.toData(),
                orderDto.hotelId(),
                orderDto.userId(),
                orderDto.roomTypeId(),

                orderDto.createdAt(),
                orderDto.updatedAt(),
                orderDto.removed()
        );
    }
}
