package org.booking_hotel.room_type_facilities.model;

import jakarta.validation.constraints.NotNull;

public record RoomTypeFacilitySaveResponse(
        @NotNull Long id,
        @NotNull Long roomTypeId,
        @NotNull Long facilityId
) {
}