package org.todo.todos.model;

import jakarta.validation.constraints.NotNull;

public record TodoSaveRequest(
        Integer id,
        @NotNull String title,
        @NotNull String description,
        @NotNull Integer userId
) {
}
