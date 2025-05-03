package org.booking_hotel.cities.model;

import jakarta.validation.constraints.NotNull;

public record CitySaveResponse(@NotNull Long id, @NotNull Long countryId) {
}
