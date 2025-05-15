package org.booking_hotel.daos.sessions;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.booking_hotel.daos.sessions.dto.SessionDto;
import org.booking_hotel.jooq.model.tables.Sessions;
import org.jooq.DSLContext;

import static org.jooq.impl.DSL.currentOffsetDateTime;
import static org.jooq.impl.DSL.defaultValue;

@Dependent
public class SessionDaoImpl implements SessionDao {

    private final Sessions s = Sessions.SESSIONS.as("s");

    @Inject
    DSLContext dsl;

    @Override
    public void cleanSessions() {
        dsl.delete(s)
                .where(s.EXPIRES.lt(currentOffsetDateTime()))
                .execute();
    }

    @Override
    public SessionDto getActiveByToken(String token) {
        return dsl.selectFrom(s)
                .where(s.TOKEN.eq(token)
                        .and(s.EXPIRES.gt(currentOffsetDateTime())))
                .fetchSingle(SessionDto::of);
    }

    @Override
    public void logout(String token) {
        dsl.update(s)
                .set(s.EXPIRES, currentOffsetDateTime())
                .where(s.TOKEN.eq(token))
                .execute();
    }

    @Override
    public void refreshSession(String token) {
        dsl.update(s)
                .set(s.EXPIRES, defaultValue(s.EXPIRES))
                .where(s.TOKEN.eq(token))
                .and(s.EXPIRES.gt(currentOffsetDateTime()))
                .execute();
    }

    @Override
    public SessionDto createSession(Long userId) {
        return dsl.insertInto(s)
                .set(s.USER_ID, userId)
                .returning()
                .fetchSingle(SessionDto::of);
    }
}
