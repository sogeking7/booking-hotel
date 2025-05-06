package org.booking_hotel.media.model;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.daos.media.dto.MediaDto;

import java.time.OffsetDateTime;

public record MediaModel(
        @NotNull Long id,
        @NotNull Long refId,
        @NotNull String refType,
        @NotNull Long fileId,

        @NotNull OffsetDateTime createdAt,
        @NotNull OffsetDateTime updatedAt,
        @NotNull Boolean removed

) {

    public static MediaModel of(MediaDto mediaDto) {
        return new MediaModel(
                mediaDto.id(),
                mediaDto.refId(),
                mediaDto.refType(),
                mediaDto.fileId(),

                mediaDto.createdAt(),
                mediaDto.updatedAt(),
                mediaDto.removed()
        );
    }
}