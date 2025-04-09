package org.todo.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.todo.dao.TodoDao;
import org.todo.dto.ErrorResponse;
import org.todo.dto.TodoDto;

import java.util.List;

@Path("/todos")
@Tag(name = "Todo", description = "Operations related to Todo items")
public class TodoResources {

    @Inject
    TodoDao todoDao;

    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTodos() {
        List<TodoDto> todoDtoList = todoDao.getTodosDto();
        if (todoDtoList.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).entity(new ErrorResponse("No todos found", 204)).build();
        }
        return Response.ok(todoDtoList).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTodo(TodoDto todoDto) {
        TodoDto createdTodo = todoDao.createTodo(todoDto, todoDto.userId);
        if (null == createdTodo) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorResponse("Failed to create new todo: " + todoDto, 500)).build();
        }
        return Response.ok(createdTodo).build();
    }
}
