package com.catalogservice.domain.model.business;

public record Address(
        String street,
        String city,
        String state,
        String postalCode,
        String country
) {
    public Address {
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("city es obligatorio");
        }
        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("country es obligatorio");
        }
    }
}