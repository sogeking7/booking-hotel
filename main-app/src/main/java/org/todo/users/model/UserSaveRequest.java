package org.todo.users.model;

import jakarta.validation.constraints.NotNull;

public record UserSaveRequest(
        Integer id,
        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull String email,
        @NotNull String password
) {
}
