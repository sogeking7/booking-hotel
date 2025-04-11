package org.todo.users;

import org.todo.jooq.model.tables.records.UserRecord;
import org.todo.users.dto.UserDto;

import java.util.List;
import java.util.function.Consumer;

public interface UserDao {

    List<UserDto> getAll();

    UserDto getById(int id);

    boolean existsById(int id);

    boolean existsByEmail(String email);

    UserDto insert(Consumer<UserRecord> fn);

    UserDto updateById(Consumer<UserRecord> fn, Integer id);

    Integer deleteById(Integer id);
}
