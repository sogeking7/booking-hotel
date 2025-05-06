package org.booking_hotel.media.model;

import jakarta.validation.constraints.NotNull;

public record MediaSaveRequest(
        Long id,
        @NotNull Long refId,
        @NotNull String refType,
        @NotNull Long fileId
) {
}