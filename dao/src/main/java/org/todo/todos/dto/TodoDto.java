package org.todo.todos.dto;

import org.todo.jooq.model.tables.records.TodoRecord;

public record TodoDto(
        int id,
        String title,
        String description,
        String createdAt,
        int userId
) {

    public TodoDto(int id, String title, String description, String createdAt) {
        this(id, title, description, createdAt, 0);
    }

    public TodoDto(String title, String description) {
        this(0, title, description, null, 0);
    }

    public static TodoDto of(TodoRecord record) {
        return new TodoDto(
                record.getId(),
                record.getTitle(),
                record.getDescription(),
                record.getCreatedAt().toString(),
                record.getUserId()
        );
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public String createdAt() {
        return createdAt;
    }

    @Override
    public int userId() {
        return userId;
    }
}
