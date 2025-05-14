package org.booking_hotel.daos.sessions;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.daos.sessions.dto.SessionDto;

public interface SessionDao {

    @NotNull
    Long getUserId(@NotNull String token);

    @NotNull
    SessionDto createSession(@NotNull Long userId); // New method to create a session

    void cleanSessions();

    void logout(@NotNull String token);

    void refreshSession(@NotNull String token);
}
