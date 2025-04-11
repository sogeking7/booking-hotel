package org.todo.todos;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.todo.todos.dto.TodoDto;
import org.todo.todos.model.TodoModel;
import org.todo.todos.model.TodoSaveRequest;
import org.todo.todos.model.TodoSaveResponse;
import org.todo.utils.BusinessException;

import java.util.List;

@Path("/todos")
@Tag(name = "Todo", description = "Operations related to Todo items")
public class TodoResource {

    @Inject
    TodoService todoService;

    @GET
    public List<TodoDto> getAllTodos() {
        return todoService.getAllTodos();
    }

    @POST
    public TodoSaveResponse saveTodo(TodoSaveRequest req) throws BusinessException {
        return todoService.saveTodo(req);
    }

    @GET
    @Path("/{id}")
    public TodoModel getTodoById(@PathParam("id") Integer id) {
        TodoDto todoDto = todoService.getTodoById(id);
        return new TodoModel(
                todoDto.id(),
                todoDto.title(),
                todoDto.description(),
                todoDto.createdAt()
        );
    }

    @DELETE
    @Path("/{id}")
    public void deleteTodoById(@PathParam("id") Integer id) {
        todoService.deleteTodoById(id);
    }
}
