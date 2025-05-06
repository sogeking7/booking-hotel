package org.booking_hotel.daos.users;

import jakarta.validation.constraints.NotNull;
import org.booking_hotel.common.BaseDao;
import org.booking_hotel.daos.users.dto.UserDto;
import org.booking_hotel.jooq.model.tables.records.UserRecord;

public interface UserDao extends BaseDao<UserDto, UserRecord, Long> {
    @NotNull
    Boolean existsById(@NotNull Long id);

    @NotNull
    Boolean existsByEmail(@NotNull String email);
}
