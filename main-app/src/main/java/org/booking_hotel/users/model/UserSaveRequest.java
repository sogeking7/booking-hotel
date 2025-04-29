package org.booking_hotel.users.model;

import jakarta.validation.constraints.NotNull;

public record UserSaveRequest(
        Long id,
        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull String email,
        @NotNull String password
) {
}
