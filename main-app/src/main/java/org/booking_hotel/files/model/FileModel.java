package org.booking_hotel.files.model;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.daos.files.dto.FileDto;

import java.time.OffsetDateTime;

public record FileModel(
        @NotNull Long id,
        @NotNull String name,
        @NotNull String urlPath,
        @NotNull String type,
        @NotNull Long size,

        @NotNull OffsetDateTime createdAt,
        @NotNull OffsetDateTime updatedAt,
        @NotNull Boolean removed

) {

    public static FileModel of(FileDto fileDto) {
        return new FileModel(
                fileDto.id(),
                fileDto.name(),
                fileDto.urlPath(),
                fileDto.type(),
                fileDto.size(),

                fileDto.createdAt(),
                fileDto.updatedAt(),
                fileDto.removed()
        );
    }
}