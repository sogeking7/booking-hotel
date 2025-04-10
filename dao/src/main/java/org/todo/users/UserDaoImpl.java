package org.todo.users;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.jooq.DSLContext;
import org.todo.jooq.model.tables.Users;
import org.todo.jooq.model.tables.records.UserRecord;
import org.todo.users.dto.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Dependent
public class UserDaoImpl implements UserDao {

    @Inject
    DSLContext dsl;

    public List<UserDto> getAll() {
        return dsl.selectFrom(Users.USERS).fetch().stream().map(UserDto::of).toList();
    }

    public UserDto getById(int id) {
        return dsl.selectFrom(Users.USERS).where(Users.USERS.ID.eq(id)).fetchSingle(UserDto::of);
    }

    public Optional<UserDto> getByEmail(String email) {
        return dsl.selectFrom(Users.USERS)
                .where(Users.USERS.EMAIL.eq(email))
                .fetchOptional(UserDto::of);
    }

    // Consumer, Supplier, Function, BiConsumer, BiFunction
    @Override
    public UserDto insert(Consumer<UserRecord> fn) {
        var record = new UserRecord();
        fn.accept(record);
        return dsl.insertInto(Users.USERS)
                .set(record)
                .returning()
                .fetchSingle(UserDto::of);
    }

    @Override
    public UserDto updateById(Consumer<UserRecord> fn, Integer id) {
        var record = new UserRecord();
        fn.accept(record);
        return dsl.update(Users.USERS)
                .set(record)
                .where(Users.USERS.ID.eq(id))
                .returning()
                .fetchSingle(UserDto::of);
    }

}
