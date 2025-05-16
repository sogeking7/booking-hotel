package org.booking_hotel.users;

import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.booking_hotel.auth.model.UserSession;
import org.booking_hotel.common.Page;
import org.booking_hotel.common.PageRequest;
import org.booking_hotel.common.model.PageResponse;
import org.booking_hotel.daos.users.dto.UserDto;
import org.booking_hotel.users.model.GetMeResponse;
import org.booking_hotel.users.model.UserModel;
import org.booking_hotel.users.model.UserSaveRequest;
import org.booking_hotel.users.model.UserSaveResponse;
import org.booking_hotel.utils.BusinessException;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import static org.booking_hotel.auth.AuthResource.SESSION_COOKIE_NAME;

@Path("/core/users")
@Tag(name = "User", description = "Operations related to User items")
public class UserResource {
    @Inject
    UserService userService;

    @Inject
    SecurityIdentity securityIdentity;

    @Authenticated
    @GET
    @Path("/me")
    public GetMeResponse getMe() {
        UserSession session = securityIdentity.getAttribute(SESSION_COOKIE_NAME);
        return GetMeResponse.of(session.user());
    }

    @RolesAllowed("admin")
    @GET
    @Path("/{id}")
    public UserModel getUserById(@PathParam("id") Long id) {
        UserDto user = userService.getUserById(id);
        return UserModel.of(user);
    }

    @RolesAllowed("admin")
    @POST
    public UserSaveResponse saveUser(@Valid UserSaveRequest req) throws BusinessException {
        return userService.saveUser(req);
    }

    @RolesAllowed("admin")
    @GET
    public PageResponse<UserModel> getAllUsers(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size) {

        PageRequest pageRequest = new PageRequest(page, size);
        Page<UserDto> userPage = userService.getAllUsers(pageRequest);

        Page<UserModel> modelPage = new Page<>(
                userPage.getContent().stream().map(UserModel::of).toList(),
                userPage.getTotalElements(),
                pageRequest
        );

        return PageResponse.of(modelPage);
    }

    @RolesAllowed("admin")
    @DELETE
    @Path("/{id}")
    public void deleteUserById(@PathParam("id") Long id) {
        userService.deleteUserById(id);
    }
}
