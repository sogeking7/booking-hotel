package org.todo.todos;

import org.todo.jooq.model.tables.records.TodoRecord;
import org.todo.todos.dto.TodoDto;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public interface TodoDao {

    List<TodoDto> getAll();

    TodoDto insert(Consumer<TodoRecord> fn);

    Optional<TodoDto> getById(Integer id);
}
