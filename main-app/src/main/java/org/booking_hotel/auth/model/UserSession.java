package org.booking_hotel.auth.model;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.jooq.model.enums.UserRole;

import java.time.OffsetDateTime;

public record UserSession(
        @NotNull String token,
        @NotNull OffsetDateTime created,
        @NotNull OffsetDateTime expires,

        @NotNull Long id,
        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull String email,
        @NotNull UserRole role
) {
}
