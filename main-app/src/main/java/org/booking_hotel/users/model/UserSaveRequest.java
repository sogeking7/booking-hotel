package org.booking_hotel.users.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public record UserSaveRequest(
        Long id,
        @NotNull @NotBlank String firstName,
        @NotNull @NotBlank String lastName,
        @NotNull @Email String email,
        @NotNull @NotBlank @Schema(defaultValue = "qwerty") String password,
        @NotNull @NotBlank @Schema(defaultValue = "user") String role
) {
}
