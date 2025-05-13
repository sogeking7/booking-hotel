package org.booking_hotel.daos.users.dto;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.jooq.model.tables.records.UserRecord;

import java.time.OffsetDateTime;

public record UserDto(
        @NotNull Long id,
        @NotNull OffsetDateTime createdAt,
        @NotNull OffsetDateTime updatedAt,
        @NotNull Boolean removed,

        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull String email,
        @NotNull String passwordHash,
        @NotNull String role
) {

    public static UserDto of(UserRecord record) {
        return new UserDto(
                record.getId(),
                record.getCreatedAt(),
                record.getUpdatedAt(),
                record.getRemoved(),

                record.getFirstName(),
                record.getLastName(),
                record.getEmail(),
                record.getPasswordHash(),
                record.getRole().getLiteral()
        );
    }
}
