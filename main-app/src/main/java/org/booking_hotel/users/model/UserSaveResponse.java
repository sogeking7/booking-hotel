package org.booking_hotel.users.model;

import jakarta.validation.constraints.NotNull;

public record UserSaveResponse(@NotNull Long id) {
}
