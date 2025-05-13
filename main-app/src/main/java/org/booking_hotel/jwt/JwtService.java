package org.booking_hotel.jwt;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@RequestScoped
@Transactional
public class JwtService {
    public String generateJwt(@NotNull String email, @NotNull Set<String> roles) {
        long now = System.currentTimeMillis() / 1000L; // current time in seconds
        long expiresAt = now + 15 * 60; // 15 minutes from now, in seconds
        return Jwt
                .issuer("booking-hotel")
                .subject(email)
                .groups(roles)
                .expiresAt(expiresAt)
                .sign();
    }
}
