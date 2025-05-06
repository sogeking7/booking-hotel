package org.booking_hotel.media.dto;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.jooq.model.tables.records.MediaRecord;

import java.time.OffsetDateTime;

public record MediaDto(
        @NotNull Long id,
        @NotNull OffsetDateTime createdAt,
        @NotNull OffsetDateTime updatedAt,
        @NotNull Boolean removed,

        @NotNull Long refId,
        @NotNull String refType,
        @NotNull Long fileId
) {
    public static MediaDto of(MediaRecord record) {
        return new MediaDto(
                record.getId(),
                record.getCreatedAt(),
                record.getUpdatedAt(),
                record.getRemoved(),

                record.getRefId(),
                record.getRefType(),
                record.getFileId()
        );
    }
}