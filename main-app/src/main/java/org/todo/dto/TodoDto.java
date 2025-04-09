package org.todo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TodoDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long id;

    @JsonProperty("title")
    public String title;

    @JsonProperty("description")
    public String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String createdAt;

    @JsonProperty(defaultValue = "user_id")
    public int userId;

    public TodoDto() {

    }

    public TodoDto(Long id, String title, String description, String createdAt, int userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    public TodoDto(Long id, String title, String description, String createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public TodoDto(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
