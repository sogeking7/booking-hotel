package org.todo.users;

import org.todo.jooq.model.tables.records.UserRecord;
import org.todo.users.dto.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public interface UserDao {

    List<UserDto> getAll();

    Optional<UserDto> getById(int id);

    Optional<UserDto> getByEmail(String email);

    UserDto insert(Consumer<UserRecord> fn);

    UserDto updateById(Consumer<UserRecord> fn, Integer id);
}
