    package org.booking_hotel.files.model;

    import jakarta.validation.constraints.NotNull;

    public record FileSaveRequest(
            Long id,
            @NotNull String name,
            @NotNull String urlPath,
            @NotNull String type,
            @NotNull Long size
    ) {
    }