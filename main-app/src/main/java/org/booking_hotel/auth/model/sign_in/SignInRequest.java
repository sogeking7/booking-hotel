package org.booking_hotel.auth.model.sign_in;

import jakarta.validation.constraints.NotNull;

public record SignInRequest(
        @NotNull String email,
        @NotNull String password
) {
}
