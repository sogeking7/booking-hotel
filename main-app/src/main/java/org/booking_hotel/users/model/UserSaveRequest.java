package org.booking_hotel.users.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.booking_hotel.jooq.model.enums.UserRole;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public record UserSaveRequest(
        Long id,
        @NotNull @NotBlank String firstName,
        @NotNull @NotBlank String lastName,
        @Email String email,
        @Schema(defaultValue = "qwerty") String password,
        @NotNull UserRole role
) {
}
