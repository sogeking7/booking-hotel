package org.booking_hotel.facilities.model;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.jooq.model.enums.FacilityTypes;

public record FacilitySaveRequest(
        Long id,
        @NotNull String name,
        @NotNull String iconRef,
        FacilityTypes type
) {
}