package org.booking_hotel.auth;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.booking_hotel.auth.model.sign_in.SignInRequest;
import org.booking_hotel.auth.model.sign_up.SignUpRequest;
import org.booking_hotel.jwt.model.JwtModel;
import org.booking_hotel.utils.BusinessException;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/auth")
@Tag(name = "Auth")
public class AuthResource {
    @Inject
    AuthService authService;

    @POST
    @Path("/sign-in")
    public JwtModel signIn(@Valid SignInRequest req) throws BusinessException {
        return authService.signIn(req);
    }

    @POST
    @Path("/sign-up")
    public JwtModel signUp(@Valid SignUpRequest req) throws BusinessException {
        return authService.signUp(req);
    }
}
