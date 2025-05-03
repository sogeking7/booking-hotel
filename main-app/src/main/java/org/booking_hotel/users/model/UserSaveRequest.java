package org.booking_hotel.users.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserSaveRequest(
        Long id,
        @NotNull @NotBlank String firstName,
        @NotNull @NotBlank String lastName,
        @NotNull @Email String email,
        @NotNull @NotBlank String password
) {
}
