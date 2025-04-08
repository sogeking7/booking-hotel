package org.todo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String password;

    @JsonProperty("first_name")
    public String firstName;

    @JsonProperty("last_name")
    public String lastName;

    @JsonProperty("email")
    public String email;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String createdAt;

    public UserDto() {}

    public UserDto(Long id, String firstName, String lastName, String email, String createdAt, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.createdAt = createdAt;
        this.password = password;
    }

    public UserDto(Long id, String firstName, String lastName, String email, String createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.createdAt = createdAt;
    }
}
