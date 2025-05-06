package org.booking_hotel.files.model;

import jakarta.validation.constraints.NotNull;

public record FileSaveResponse(
        @NotNull Long id,
        @NotNull String name,
        @NotNull String urlPath,
        @NotNull String type,
        @NotNull Long size
) {
}