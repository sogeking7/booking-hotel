package org.todo.todos.dto;

public class TodoDto {
    public Long id;
    public String title;
    public String description;
    public String createdAt;
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
