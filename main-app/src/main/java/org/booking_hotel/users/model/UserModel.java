package org.booking_hotel.users.model;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.daos.users.dto.UserDto;
import org.booking_hotel.jooq.model.enums.UserRole;

import java.time.OffsetDateTime;

public record UserModel(
        @NotNull Long id,
        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull String email,
        @NotNull UserRole role,

        @NotNull OffsetDateTime createdAt,
        @NotNull OffsetDateTime updatedAt,
        @NotNull Boolean removed
) {

    public static UserModel of(UserDto userDto) {
        return new UserModel(
                userDto.id(),
                userDto.firstName(),
                userDto.lastName(),
                userDto.email(),
                userDto.role(),
                userDto.createdAt(),
                userDto.updatedAt(),
                userDto.removed()
        );
    }
}
