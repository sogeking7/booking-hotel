package org.todo.todos;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.jooq.DSLContext;
import org.todo.jooq.model.tables.Todos;
import org.todo.jooq.model.tables.records.TodoRecord;
import org.todo.todos.dto.TodoDto;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Dependent
public class TodoDaoImpl implements TodoDao {

    @Inject
    DSLContext dsl;

    @Override
    public List<TodoDto> getAll() {
        return dsl.selectFrom(Todos.TODOS).fetch()
                .stream().map(TodoDto::of).toList();
    }

    @Override
    public TodoDto insert(Consumer<TodoRecord> fn) {
        var record = new TodoRecord();
        fn.accept(record);
        return dsl.insertInto(Todos.TODOS)
                .set(record)
                .returning()
                .fetchSingle(TodoDto::of);
    }

    @Override
    public Optional<TodoDto> getById(Integer id) {
        return Optional.ofNullable(
                dsl.selectFrom(Todos.TODOS).where(Todos.TODOS.ID.eq(id)).fetchOne(TodoDto::of)
        );
    }
}
