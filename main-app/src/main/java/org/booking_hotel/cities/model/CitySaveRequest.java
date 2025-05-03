package org.booking_hotel.cities.model;

import jakarta.validation.constraints.NotNull;

public record CitySaveRequest(
        Long id,
        @NotNull String name,
        Long countryId
) {
}
