package org.booking_hotel.auth.filter;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import org.booking_hotel.auth.AuthResource;
import org.booking_hotel.daos.sessions.SessionDao;
import org.jooq.exception.NoDataFoundException;

import java.io.IOException;
import java.security.Principal;

@Provider
@Authenticated
@ApplicationScoped
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Inject
    SessionDao sessionDao;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        Cookie sessionCookie = requestContext.getCookies().get(AuthResource.SESSION_COOKIE_NAME);

        if (sessionCookie == null || sessionCookie.getValue() == null || sessionCookie.getValue().isEmpty()) {
            abortWithUnauthorized(requestContext, "Missing session cookie");
            return;
        }

        String token = sessionCookie.getValue();
        try {
            Long userId = sessionDao.getUserId(token);

            final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
            requestContext.setSecurityContext(new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return () -> String.valueOf(userId);
                }

                @Override
                public boolean isUserInRole(String role) {
                    return true;
                }

                @Override
                public boolean isSecure() {
                    return currentSecurityContext.isSecure();
                }

                @Override
                public String getAuthenticationScheme() {
                    return "SESSION_COOKIE_AUTH";
                }
            });


        } catch (NoDataFoundException e) {
            abortWithUnauthorized(requestContext, "Invalid or expired session token");
        } catch (Exception e) {
            System.err.println("Authentication error: " + e.getMessage());
            abortWithUnauthorized(requestContext, "Authentication processing error");
        }
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext, String message) {
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"error\": \"Unauthorized\", \"message\": \"" + message + "\"}")
                        .type("application/json")
                        .build());
    }
}
