package org.booking_hotel.daos.room_type_facilities.dto;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.jooq.model.tables.records.RoomTypeFacilityRecord;

import java.time.OffsetDateTime;

public record RoomTypeFacilityDto(
        @NotNull Long id,
        @NotNull OffsetDateTime createdAt,
        @NotNull OffsetDateTime updatedAt,
        @NotNull Boolean removed,

        @NotNull Long roomTypeId,
        @NotNull Long facilityId
) {
    public static RoomTypeFacilityDto of(RoomTypeFacilityRecord record) {
        return new RoomTypeFacilityDto(
                record.getId(),
                record.getCreatedAt(),
                record.getUpdatedAt(),
                record.getRemoved(),

                record.getRoomTypeId(),
                record.getFacilityId()
        );
    }
}