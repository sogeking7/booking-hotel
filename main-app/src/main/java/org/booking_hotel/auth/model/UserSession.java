package org.booking_hotel.auth.model;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.daos.users.dto.UserDto;

import java.time.OffsetDateTime;

public record UserSession(
        @NotNull String token,
        @NotNull OffsetDateTime created,
        @NotNull OffsetDateTime expires,

        @NotNull UserDto user
) {
}
