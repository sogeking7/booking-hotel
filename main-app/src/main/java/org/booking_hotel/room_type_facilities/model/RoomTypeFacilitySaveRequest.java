package org.booking_hotel.room_type_facilities.model;

import jakarta.validation.constraints.NotNull;

public record RoomTypeFacilitySaveRequest(
        Long id,
        @NotNull Long roomTypeId,
        @NotNull Long facilityId
) {
}