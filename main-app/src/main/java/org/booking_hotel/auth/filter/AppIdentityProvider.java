package org.booking_hotel.auth.filter;


import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.IdentityProvider;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.QuarkusPrincipal;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.booking_hotel.auth.model.UserSession;
import org.booking_hotel.daos.sessions.SessionDao;
import org.booking_hotel.daos.sessions.dto.SessionDto;
import org.booking_hotel.daos.users.UserDao;
import org.booking_hotel.daos.users.dto.UserDto;
import org.booking_hotel.jooq.model.enums.UserRole;
import org.jooq.exception.NoDataFoundException;

import java.util.Optional;
import java.util.Set;

import static org.booking_hotel.auth.AuthResource.SESSION_COOKIE_NAME;

@ApplicationScoped
public class AppIdentityProvider implements IdentityProvider<AppAuthRequest> {

    @Inject
    UserDao userDao;

    @Inject
    SessionDao sessionDao;


    @Override
    public Class<AppAuthRequest> getRequestType() {
        return AppAuthRequest.class;
    }

    @Override
    public Uni<SecurityIdentity> authenticate(AppAuthRequest request, AuthenticationRequestContext context) {
        return context.runBlocking(() -> {
            QuarkusSecurityIdentity.Builder builder = QuarkusSecurityIdentity.builder();

            getActiveUserSessionByToken(request.getSessionId()).ifPresentOrElse(session -> {
                UserRole userRole = session.user().role();
                builder.addRoles(Set.of(userRole.name()));
                builder.addAttribute(SESSION_COOKIE_NAME, session);
                builder.setPrincipal(new QuarkusPrincipal(session.user().email()));
            }, () -> {
                builder.setAnonymous(true);
            });
            return builder.build();
        });
    }

    private Optional<UserSession> getActiveUserSessionByToken(String token) {
        try {
            SessionDto session = sessionDao.getActiveByToken(token);
            UserDto user = userDao.getById(session.userId());

            return Optional.of(new UserSession(
                    session.token(),
                    session.created(),
                    session.expires(),
                    user
            ));
        } catch (NoDataFoundException e) {
            return Optional.empty();
        }

    }
}