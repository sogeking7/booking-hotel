package org.todo.todos;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.todo.jooq.model.tables.records.TodoRecord;
import org.todo.todos.dto.TodoDto;
import org.todo.todos.model.TodoSaveRequest;
import org.todo.todos.model.TodoSaveResponse;
import org.todo.users.UserDao;
import org.todo.utils.BusinessException;

import java.util.List;
import java.util.function.Consumer;

@RequestScoped
@Transactional
public class TodoService {

    @Inject
    TodoDao todoDao;

    @Inject
    UserDao userDao;

    public TodoSaveResponse saveTodo(TodoSaveRequest req) throws BusinessException {
        boolean isUserExists = userDao.existsById(req.userId());

        if (!isUserExists) {
            throw new BusinessException(
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    "todo.save.userNotExists",
                    "User with id " + req.userId() + " does not exist"
            );
        }

        Consumer<TodoRecord> fn = record -> {
            record.setTitle(req.title());
            record.setDescription(req.description());
            record.setUserId(req.userId());
        };

        TodoDto createdTodo = req.id() == null ? todoDao.insert(fn) : todoDao.updateById(fn, req.id());

        return new TodoSaveResponse(createdTodo.id());
    }

    public List<TodoDto> getAllTodos() {
        return todoDao.getAll();
    }

    public TodoDto getTodoById(Integer id) {
        return todoDao.getById(id);
    }

    public void deleteTodoById(Integer id) {
        todoDao.deleteById(id);
    }
}
