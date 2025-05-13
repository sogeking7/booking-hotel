package org.booking_hotel.jwt.model;

import jakarta.validation.constraints.NotNull;

public record JwtModel(
        @NotNull String accessToken
) {
}
