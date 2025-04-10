package org.todo.todos.model;

import org.todo.todos.dto.TodoDto;

public record TodoModel(
        Integer id,
        String title,
        String description,
        String createdAt
) {

    public static TodoModel of(TodoDto todoDto) {
        return new TodoModel(
                todoDto.id(),
                todoDto.title(),
                todoDto.description(),
                todoDto.createdAt()
        );
    }
}
