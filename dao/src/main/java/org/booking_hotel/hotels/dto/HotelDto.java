package org.booking_hotel.hotels.dto;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.jooq.model.tables.records.HotelRecord;

import java.time.OffsetDateTime;

public record HotelDto(
        @NotNull Long id,
        @NotNull OffsetDateTime createdAt,
        @NotNull OffsetDateTime updatedAt,
        @NotNull Boolean removed,

        @NotNull String name,
        @NotNull String address,
        @NotNull String phone,
        Long cityId
) {
    public static HotelDto of(HotelRecord record) {
        return new HotelDto(
                record.getId(),
                record.getCreatedAt(),
                record.getUpdatedAt(),
                record.getRemoved(),

                record.getName(),
                record.getAddress(),
                record.getPhone(),
                record.getCityId()
        );
    }
}