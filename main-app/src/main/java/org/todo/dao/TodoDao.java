package org.todo.dao;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.todo.dto.TodoDto;
import org.todo.jooq.model.tables.Todos;
import org.todo.jooq.model.tables.records.TodoRecord;

import java.util.List;

@Singleton
public class TodoDao {

    @Inject
    DSLContext dsl;

    public List<TodoDto> getTodosDto() {
        Result<TodoRecord> result = dsl.selectFrom(Todos.TODOS).fetch();

        return result.stream().map(r -> new TodoDto(
                r.getId().longValue(),
                r.getTitle(),
                r.getDescription(),
                r.getCreatedAt().toString(),
                r.getUserId()
        )).toList();
    }

    public TodoDto createTodo(TodoDto todoDto, int userId) {
        TodoRecord todoRecord = dsl.insertInto(Todos.TODOS,
                        Todos.TODOS.TITLE,
                        Todos.TODOS.DESCRIPTION,
                        Todos.TODOS.USER_ID
                )
                .values(todoDto.title, todoDto.description, userId)
                .returning()
                .fetchOne();

        if (todoRecord != null) {
            return new TodoDto(
                    todoRecord.getId().longValue(),
                    todoRecord.getTitle(),
                    todoRecord.getDescription(),
                    todoRecord.getCreatedAt().toString(),
                    todoRecord.getUserId()
            );
        }

        return null;
    }
}
