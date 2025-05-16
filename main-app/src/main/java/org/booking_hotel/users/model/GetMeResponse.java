package org.booking_hotel.users.model;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.daos.users.dto.UserDto;
import org.booking_hotel.jooq.model.enums.UserRole;

public record GetMeResponse(
        @NotNull Long id,
        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull String email,
        @NotNull UserRole role
) {

    public static GetMeResponse of(UserDto userDto) {
        return new GetMeResponse(
                userDto.id(),
                userDto.firstName(),
                userDto.lastName(),
                userDto.email(),
                userDto.role()
        );
    }
}
