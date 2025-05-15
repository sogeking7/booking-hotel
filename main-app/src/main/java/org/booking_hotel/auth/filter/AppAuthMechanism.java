package org.booking_hotel.auth.filter;

import io.quarkus.security.identity.IdentityProviderManager;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.AuthenticationRequest;
import io.quarkus.vertx.http.runtime.security.ChallengeData;
import io.quarkus.vertx.http.runtime.security.HttpAuthenticationMechanism;
import io.quarkus.vertx.http.runtime.security.HttpCredentialTransport;
import io.quarkus.vertx.http.runtime.security.HttpSecurityUtils;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.Set;

@Alternative
@Priority(1)
@ApplicationScoped
public class AppAuthMechanism implements HttpAuthenticationMechanism {

    final String COOKIE_NAME = "session_token";

    @Override
    public Uni<SecurityIdentity> authenticate(RoutingContext ctx, IdentityProviderManager identityProviderManager) {
        return Optional.ofNullable(ctx.request().getHeader(HttpHeaders.AUTHORIZATION))
                .filter(str -> str.contains(" "))
                .map(str -> str.split(" ")[1])
                .or(() -> Optional.ofNullable(ctx.request().getCookie(COOKIE_NAME))
                        .map(Cookie::getValue)
                )
                .map(AppAuthRequest::of)
                .map(credential -> {
                    HttpSecurityUtils.setRoutingContextAttribute(credential, ctx);
                    return identityProviderManager.authenticate(credential);
                }).orElseGet(() -> {
                    return Uni.createFrom().optional(Optional.empty());
                });
    }

    @Override
    public Uni<ChallengeData> getChallenge(RoutingContext context) {
        return Uni.createFrom().nullItem();
    }

    @Override
    public Set<Class<? extends AuthenticationRequest>> getCredentialTypes() {
        return Set.of(AppAuthRequest.class);
    }

    @Override
    public Uni<HttpCredentialTransport> getCredentialTransport(RoutingContext ctx) {
        String authHeader = ctx.request().getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isNotEmpty(authHeader)) {
            return Uni.createFrom().item(new HttpCredentialTransport(HttpCredentialTransport.Type.AUTHORIZATION, "bearer"));
        }
        return Uni.createFrom().item(new HttpCredentialTransport(HttpCredentialTransport.Type.COOKIE, COOKIE_NAME));
    }
}
