package org.booking_hotel.auth;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.*;
import org.booking_hotel.auth.model.sign_in.SignInRequest;
import org.booking_hotel.auth.model.sign_up.SignUpRequest;
import org.booking_hotel.daos.sessions.SessionDao;
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

    @Inject
    SessionDao sessionDao;

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

        return Response.ok("Signed in successfully").cookie(sessionCookie).build();
    }

    @POST
    @Path("/sign-up")
    public Response signUp(@Valid SignUpRequest req) throws BusinessException {
        SessionDto session = authService.signUp(req);
        NewCookie sessionCookie = createSessionCookie(session);

        return Response.ok("Signed up successfully").cookie(sessionCookie).build();
    }

    @POST
    @Path("/logout")
    public Response logout(@Context HttpHeaders headers) {
        Cookie sessionCookie = headers.getCookies().get(SESSION_COOKIE_NAME);

        if (sessionCookie != null && sessionCookie.getValue() != null) {
            String token = sessionCookie.getValue();
            try {
                sessionDao.logout(token);
            } catch (Exception e) {
                System.err.println("Error during server-side logout: " + e.getMessage());
            }
        }

        NewCookie deleteCookie = new NewCookie.Builder(SESSION_COOKIE_NAME)
                .value(null)
                .path("/")
                .maxAge(0)
                .secure(false)
                .httpOnly(true)
                .build();

        return Response.ok("Logged out successfully").cookie(deleteCookie).build();
    }
}
