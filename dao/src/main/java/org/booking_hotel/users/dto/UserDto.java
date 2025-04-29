package org.booking_hotel.users.dto;

import org.booking_hotel.jooq.model.tables.records.UserRecord;

public record UserDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String password,
        String createdAt
) {

    public UserDto(Long id, String firstName, String lastName, String email) {
        this(id, firstName, lastName, email, null, null);
    }

    public UserDto(Long id, String firstName, String lastName, String email, String createdAt) {
        this(id, firstName, lastName, email, null, createdAt);
    }

    public static UserDto of(UserRecord record) {
        return new UserDto(
                record.getId(),
                record.getFirstName(),
                record.getLastName(),
                record.getEmail(),
                record.getCreatedAt().toString()
        );
    }

    @Override
    public Long id() {
        return id;
    }

    @Override
    public String firstName() {
        return firstName;
    }

    @Override
    public String lastName() {
        return lastName;
    }

    @Override
    public String email() {
        return email;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public String createdAt() {
        return createdAt;
    }
}
