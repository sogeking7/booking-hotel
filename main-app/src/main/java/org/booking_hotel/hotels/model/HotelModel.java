package org.booking_hotel.hotels.model;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.daos.hotels.dto.HotelDto;

import java.time.OffsetDateTime;

public record HotelModel(
        @NotNull Long id,
        @NotNull String name,
        @NotNull String address,
        @NotNull String phone,
        Long cityId,

        @NotNull OffsetDateTime createdAt,
        @NotNull OffsetDateTime updatedAt,
        @NotNull Boolean removed

) {

    public static HotelModel of(HotelDto hotelDto) {
        return new HotelModel(
                hotelDto.id(),
                hotelDto.name(),
                hotelDto.address(),
                hotelDto.phone(),
                hotelDto.cityId(),

                hotelDto.createdAt(),
                hotelDto.updatedAt(),
                hotelDto.removed()
        );
    }
}