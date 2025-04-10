package org.todo.todos.model;

public record TodoSaveRequest(
        String title,
        String description,
        Integer userId
) {
}
