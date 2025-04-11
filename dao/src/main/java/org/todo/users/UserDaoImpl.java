package org.todo.users;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.jooq.DSLContext;
import org.todo.jooq.model.tables.Users;
import org.todo.jooq.model.tables.records.UserRecord;
import org.todo.users.dto.UserDto;

import java.util.List;
import java.util.function.Consumer;

@Dependent
public class UserDaoImpl implements UserDao {

    private final Users u = Users.USERS.as("u");

    @Inject
    DSLContext dsl;

    public List<UserDto> getAll() {
        return dsl.selectFrom(u)
                .fetch().stream().map(UserDto::of).toList();
    }

    public UserDto getById(int id) {
        return dsl.selectFrom(u)
                .where(u.ID.eq(id))
                .fetchSingle(UserDto::of);

    }

    @Override
    public boolean existsById(int id) {
        return dsl.fetchExists(u, u.ID.eq(id));
    }

    public boolean existsByEmail(String email) {
        return dsl.fetchExists(u, u.EMAIL.eq(email));
    }

    // Consumer, Supplier, Function, BiConsumer, BiFunction
    @Override
    public UserDto insert(Consumer<UserRecord> fn) {
        var record = new UserRecord();
        fn.accept(record);
        return dsl.insertInto(u)
                .set(record)
                .returning()
                .fetchSingle(UserDto::of);
    }

    @Override
    public UserDto updateById(Consumer<UserRecord> fn, Integer id) {
        var record = new UserRecord();
        fn.accept(record);
        return dsl.update(u)
                .set(record)
                .where(u.ID.eq(id))
                .returning()
                .fetchSingle(UserDto::of);
    }
}
