package org.booking_hotel.bed_types.model;

import jakarta.validation.constraints.NotNull;

public record BedTypeSaveRequest(
        Long id,
        @NotNull String name,
        @NotNull String iconRef
) {
}