package org.booking_hotel.auth;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import org.booking_hotel.auth.model.sign_in.SignInRequest;
import org.booking_hotel.auth.model.sign_up.SignUpRequest;
import org.booking_hotel.daos.sessions.dto.SessionDto;
import org.booking_hotel.utils.BusinessException;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Path("/auth")
@Tag(name = "Auth")
public class AuthResource {
    public static final String SESSION_COOKIE_NAME = "session_token";
    @Inject
    AuthService authService;

    private static NewCookie createSessionCookie(@NotNull SessionDto session) {
        String token = session.token();
        OffsetDateTime expires = session.expires();
        OffsetDateTime now = OffsetDateTime.now();

        long maxAgeSeconds = 0;
        if (expires.isAfter(now)) {
            maxAgeSeconds = ChronoUnit.SECONDS.between(now, expires);
        }

        return new NewCookie.Builder(SESSION_COOKIE_NAME)
                .value(token)
                .path("/")
                .maxAge((int) maxAgeSeconds)
                .secure(false)
                .httpOnly(true)
                .sameSite(NewCookie.SameSite.STRICT)
                .build();
    }

    @POST
    @Path("/sign-in")
    public Response signIn(@Valid SignInRequest req) throws BusinessException {
        SessionDto session = authService.signIn(req);
        NewCookie sessionCookie = createSessionCookie(session);

        return Response.ok().cookie(sessionCookie).build();
    }

    @POST
    @Path("/sign-up")
    public Response signUp(@Valid SignUpRequest req) throws BusinessException {
        SessionDto session = authService.signUp(req);
        NewCookie sessionCookie = createSessionCookie(session);

        return Response.ok().cookie(sessionCookie).build();
    }
}
