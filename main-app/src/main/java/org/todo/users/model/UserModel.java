package org.todo.users.model;

import org.todo.users.dto.UserDto;

public record UserModel(Integer id, String firstName, String lastName, String email) {

    public static UserModel of(UserDto userDto) {
        return new UserModel(
                userDto.id(),
                userDto.firstName(),
                userDto.lastName(),
                userDto.email()
        );
    }
}
