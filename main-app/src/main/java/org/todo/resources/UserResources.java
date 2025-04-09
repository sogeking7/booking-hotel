package org.todo.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.todo.dao.UserDao;
import org.todo.dto.ErrorResponse;
import org.todo.dto.UserDto;

import java.util.List;

@Path("/users")
@Tag(name = "User", description = "Operations related to User items")
public class UserResources {
    @Inject
    UserDao userRepository;

    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getUserById(@PathParam("id") int id) {
        UserDto userDto = userRepository.getUserById(id);
        if (null == userDto) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("User not found with id: " + id, 404)).build();
        }
        return Response.ok(userDto).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(UserDto userDto) {
        String password = userDto.password;

        boolean isUserEmailExists = userRepository.getUserByEmail(userDto.email) != null;
        if (isUserEmailExists) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResponse("User with email " + userDto.email + " already exists.", 409))
                    .build();
        }

        UserDto createdUser = userRepository.createUser(userDto, password);
        if (null == createdUser) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorResponse("Failed to create new user: " + userDto, 500)).build();
        }
        return Response.ok(createdUser).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        List<UserDto> userDtoList = userRepository.getUsersDto();
        if (userDtoList.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).entity(new ErrorResponse("No users found", 204)).build();
        }
        return Response.ok(userDtoList).build();
    }

}
