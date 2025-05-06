package org.booking_hotel.bed_types.model;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.daos.bed_types.dto.BedTypeDto;

import java.time.OffsetDateTime;

public record BedTypeModel(
        @NotNull Long id,
        @NotNull String name,
        @NotNull String iconRef,

        @NotNull OffsetDateTime createdAt,
        @NotNull OffsetDateTime updatedAt,
        @NotNull Boolean removed
) {
    public static BedTypeModel of(BedTypeDto bedTypeDto) {
        return new BedTypeModel(
                bedTypeDto.id(),
                bedTypeDto.name(),
                bedTypeDto.iconRef(),

                bedTypeDto.createdAt(),
                bedTypeDto.updatedAt(),
                bedTypeDto.removed()
        );
    }
}