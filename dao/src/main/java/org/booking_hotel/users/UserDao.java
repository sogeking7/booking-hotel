package org.booking_hotel.users;

import org.booking_hotel.jooq.model.tables.records.UserRecord;
import org.booking_hotel.users.dto.UserDto;

import java.util.List;
import java.util.function.Consumer;

public interface UserDao {

    List<UserDto> getAll();

    UserDto getById(Long id);

    boolean existsById(Long id);

    boolean existsByEmail(String email);

    UserDto insert(Consumer<UserRecord> fn);

    UserDto updateById(Consumer<UserRecord> fn, Long id);

    Integer deleteById(Long id);
}
