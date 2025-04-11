package org.todo.todos;

import jakarta.validation.constraints.NotNull;
import org.todo.jooq.model.tables.records.TodoRecord;
import org.todo.todos.dto.TodoDto;

import java.util.List;
import java.util.function.Consumer;

public interface TodoDao {

    List<TodoDto> getAll();

    TodoDto insert(Consumer<TodoRecord> fn);

    TodoDto getById(Integer id);

    TodoDto updateById(Consumer<TodoRecord> fn, @NotNull Integer id);

    Integer deleteById(Integer id);
}
