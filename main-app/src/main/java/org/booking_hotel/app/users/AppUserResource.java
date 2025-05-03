package org.booking_hotel.app.users;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.booking_hotel.app.users.model.AppUserModel;
import org.booking_hotel.users.UserService;
import org.booking_hotel.users.dto.UserDto;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/core/app/users")
@Tag(name = "User", description = "Operations related to User items")
public class AppUserResource {
    @Inject
    UserService userService;

    @GET
    @Path("/{id}")
    public AppUserModel getUserById(@PathParam("id") Long id) {
        UserDto user = userService.getUserById(id);
        return new AppUserModel(user.id(), user.firstName(), user.lastName(), user.email());
    }
}
