package org.booking_hotel.media.model;

import jakarta.validation.constraints.NotNull;

public record MediaSaveResponse(
        @NotNull Long id,
        @NotNull Long refId,
        @NotNull String refType,
        @NotNull Long fileId
) {
}