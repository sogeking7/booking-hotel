package org.booking_hotel.app.users.model;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.users.dto.UserDto;

public record AppUserModel(
        @NotNull Long id,
        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull String email
) {

    public static AppUserModel of(UserDto userDto) {
        return new AppUserModel(
                userDto.id(),
                userDto.firstName(),
                userDto.lastName(),
                userDto.email()
        );
    }
}
