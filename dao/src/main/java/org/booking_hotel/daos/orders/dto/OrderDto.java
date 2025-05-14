package org.booking_hotel.daos.orders.dto;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.jooq.model.tables.records.OrderRecord;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record OrderDto(
        @NotNull Long id,
        @NotNull OffsetDateTime createdAt,
        @NotNull OffsetDateTime updatedAt,
        @NotNull Boolean removed,

        @NotNull LocalDate fromDate,
        @NotNull LocalDate toData,
        @NotNull Long hotelId,
        @NotNull Long userId,
        @NotNull Long roomTypeId

) {
    public static OrderDto of(OrderRecord record) {
        return new OrderDto(
                record.getId(),
                record.getCreatedAt(),
                record.getUpdatedAt(),
                record.getRemoved(),


                record.getFromDate(),
                record.getToData(),
                record.getHotelId(),
                record.getUserId(),
                record.getRoomTypeId()
        );
    }
}
