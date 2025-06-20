package org.booking_hotel.hotels.model;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.daos.hotels.dto.HotelDto;
import org.booking_hotel.hotels.model.HotelModel;
import org.booking_hotel.hotels.model.RoomTypeModel;

import java.time.OffsetDateTime;
import java.util.List;

public record HotelDetailModel(
        @NotNull Long id,
        @NotNull String name,
        @NotNull String address,
        @NotNull String phone,
        Long cityId,
        List<RoomTypeModel> roomTypes,

        @NotNull OffsetDateTime createdAt,
        @NotNull OffsetDateTime updatedAt,
        @NotNull Boolean removed
) {
    public static HotelDetailModel of(HotelDto hotelDto, List<RoomTypeModel> roomTypes) {
        return new HotelDetailModel(
                hotelDto.id(),
                hotelDto.name(),
                hotelDto.address(),
                hotelDto.phone(),
                hotelDto.cityId(),
                roomTypes,

                hotelDto.createdAt(),
                hotelDto.updatedAt(),
                hotelDto.removed()
        );
    }

    public static HotelDetailModel from(HotelModel hotelModel, List<RoomTypeModel> roomTypes) {
        return new HotelDetailModel(
                hotelModel.id(),
                hotelModel.name(),
                hotelModel.address(),
                hotelModel.phone(),
                hotelModel.cityId(),
                roomTypes,

                hotelModel.createdAt(),
                hotelModel.updatedAt(),
                hotelModel.removed()
        );
    }
}
