package org.booking_hotel.daos.sessions.dto;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.jooq.model.tables.records.SessionRecord;

import java.time.OffsetDateTime;

public record SessionDto(
        @NotNull String token,
        @NotNull Long user_id,
        @NotNull OffsetDateTime created,
        @NotNull OffsetDateTime expires
) {

    public static SessionDto of(SessionRecord record) {
        return new SessionDto(
                record.getToken(),
                record.getUserId(),
                record.getCreated(),
                record.getExpires()
        );
    }
}
