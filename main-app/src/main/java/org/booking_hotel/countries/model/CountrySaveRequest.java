package org.booking_hotel.countries.model;

import jakarta.validation.constraints.NotNull;

public record CountrySaveRequest(
        Long id,
        @NotNull String name,
        @NotNull String code,
        @NotNull String currency) {
}
