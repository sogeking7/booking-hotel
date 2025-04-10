package org.todo.users.model;

public record UserSaveRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
