package org.todo.todos;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.jooq.DSLContext;
import org.todo.jooq.model.tables.Todos;
import org.todo.jooq.model.tables.records.TodoRecord;
import org.todo.todos.dto.TodoDto;

import java.util.List;
import java.util.function.Consumer;

@Dependent
public class TodoDaoImpl implements TodoDao {

    private final Todos td = Todos.TODOS.as("td");

    @Inject
    DSLContext dsl;

    @Override
    public List<TodoDto> getAll() {
        return dsl.selectFrom(td).fetch()
                .stream().map(TodoDto::of).toList();
    }

    @Override
    public TodoDto insert(Consumer<TodoRecord> fn) {
        var record = new TodoRecord();
        fn.accept(record);
        return dsl.insertInto(td)
                .set(record)
                .returning()
                .fetchSingle(TodoDto::of);
    }

    @Override
    public TodoDto getById(Integer id) {
        return dsl.selectFrom(td)
                .where(td.ID.eq(id))
                .fetchSingle(TodoDto::of);
    }

    @Override
    public TodoDto updateById(Consumer<TodoRecord> fn, Integer id) {
        var record = new TodoRecord();
        fn.accept(record);
        return dsl.update(td)
                .set(record)
                .where(td.ID.eq(id))
                .returning()
                .fetchSingle(TodoDto::of);
    }

    @Override
    public Integer delete(Integer id) {
        return dsl.deleteFrom(td)
                .where(td.ID.eq(id))
                .execute();
    }
}
