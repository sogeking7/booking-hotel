package org.booking_hotel.users;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import org.booking_hotel.auth.filter.Authenticated;
import org.booking_hotel.daos.users.dto.UserDto;
import org.booking_hotel.users.model.UserModel;
import org.booking_hotel.users.model.UserSaveRequest;
import org.booking_hotel.users.model.UserSaveResponse;
import org.booking_hotel.utils.BusinessException;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/core/users")
@Tag(name = "User", description = "Operations related to User items")
public class UserResource {
    @Inject
    UserService userService;

    @Authenticated
    @GET
    @Path("/me")
    public String getMe(@Context SecurityContext securityContext) {
        return securityContext.getUserPrincipal().getName();
    }

    @GET
    @Path("/{id}")
    public UserModel getUserById(@PathParam("id") Long id) {
        UserDto user = userService.getUserById(id);
        return UserModel.of(user);
    }

    @POST
    public UserSaveResponse saveUser(@Valid UserSaveRequest req) throws BusinessException {
        return userService.saveUser(req);
    }

    @GET
    public List<UserModel> getAllUsers() {
        return userService.getAllUsers().stream().map(UserModel::of).toList();
    }

    @DELETE
    @Path("/{id}")
    public void deleteUserById(@PathParam("id") Long id) {
        userService.deleteUserById(id);
    }
}
