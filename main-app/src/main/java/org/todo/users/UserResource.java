package org.todo.users;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.todo.users.dto.UserDto;
import org.todo.users.model.UserModel;
import org.todo.users.model.UserSaveRequest;
import org.todo.users.model.UserSaveResponse;
import org.todo.utils.BusinessException;

import java.util.List;

@Path("/users")
@Tag(name = "User", description = "Operations related to User items")
public class UserResource {
    @Inject
    UserService userService;

    @GET
    @Path("/{id}")
    public UserModel getUserById(@PathParam("id") int id) {
        UserDto user = userService.getUserById(id);
        return new UserModel(user.id(), user.firstName(), user.lastName(), user.email());
    }

    @POST
    public UserSaveResponse saveUser(@Valid UserSaveRequest req) throws BusinessException {
        return userService.saveUser(req);
    }

    @GET
    public List<UserModel> getAllUsers() {
        return userService.getAllUsers().stream().map(UserModel::of).toList();
    }

}
