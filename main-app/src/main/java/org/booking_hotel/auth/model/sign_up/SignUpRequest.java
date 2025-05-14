package org.booking_hotel.auth.model.sign_up;

import jakarta.validation.constraints.NotNull;

public record SignUpRequest(
        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull String email,
        @NotNull String password
) {
}
