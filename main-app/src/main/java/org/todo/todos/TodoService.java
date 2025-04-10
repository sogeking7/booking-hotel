package org.todo.todos;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.todo.exception.BusinessException;
import org.todo.todos.dto.TodoDto;
import org.todo.todos.model.TodoSaveRequest;
import org.todo.todos.model.TodoSaveResponse;
import org.todo.users.UserDao;

import java.util.List;

@RequestScoped
@Transactional
public class TodoService {

    @Inject
    TodoDao todoDao;

    @Inject
    UserDao userDao;

    public TodoSaveResponse createTodo(TodoSaveRequest req) throws BusinessException {
        boolean isUserExists = userDao.getById(req.userId()).isPresent();

        if (!isUserExists) {
            throw new BusinessException(
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    "User with id " + req.userId() + " does not exist"
            );
        }

        TodoDto createdTodo = todoDao.insert(record -> {
            record.setTitle(req.title());
            record.setDescription(req.description());
            record.setUserId(req.userId());
        });

        return new TodoSaveResponse(createdTodo.id());
    }

    public List<TodoDto> getAllTodos() {
        return todoDao.getAll();
    }

    public TodoDto getTodoById(Integer id) throws BusinessException {
        return todoDao.getById(id)
                .orElseThrow(() -> new BusinessException(
                        Response.Status.NOT_FOUND.getStatusCode(),
                        "Todo with id " + id + " not found"
                ));
    }

}
