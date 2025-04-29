package org.booking_hotel.users.model;

import org.booking_hotel.users.dto.UserDto;

public record UserModel(Long id, String firstName, String lastName, String email) {

    public static UserModel of(UserDto userDto) {
        return new UserModel(
                userDto.id(),
                userDto.firstName(),
                userDto.lastName(),
                userDto.email()
        );
    }
}
