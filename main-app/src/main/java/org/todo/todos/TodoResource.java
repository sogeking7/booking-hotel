package org.todo.todos;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.todo.exception.BusinessException;
import org.todo.todos.dto.TodoDto;
import org.todo.todos.model.TodoModel;
import org.todo.todos.model.TodoSaveRequest;
import org.todo.todos.model.TodoSaveResponse;

import java.util.List;

@Path("/todos")
@Tag(name = "Todo", description = "Operations related to Todo items")
public class TodoResource {

    @Inject
    TodoService todoService;

    @GET()
    public List<TodoDto> getAllTodos() {
        return todoService.getAllTodos();
    }

    @POST
    public TodoSaveResponse createTodo(TodoSaveRequest req) throws BusinessException {
        return todoService.createTodo(req);
    }

    @GET()
    @Path("/{id}")
    public TodoModel getTodoById(@PathParam("id") Integer id) throws BusinessException {
        TodoDto todoDto = todoService.getTodoById(id);
        return new TodoModel(
                todoDto.id(),
                todoDto.title(),
                todoDto.description(),
                todoDto.createdAt()
        );
    }
}
