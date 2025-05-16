package org.booking_hotel.daos.users;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.booking_hotel.common.Page;
import org.booking_hotel.common.PageRequest;
import org.booking_hotel.daos.users.dto.UserDto;
import org.booking_hotel.jooq.model.tables.Users;
import org.booking_hotel.jooq.model.tables.records.UserRecord;
import org.jooq.DSLContext;

import java.util.List;
import java.util.function.Consumer;

@Dependent
public class UserDaoImpl implements UserDao {

    private final Users u = Users.USERS.as("u");

    @Inject
    DSLContext dsl;

    @Override
    public List<UserDto> getAll() {
        return dsl.selectFrom(u)
                .where(u.REMOVED.isFalse())
                .fetch(UserDto::of);
    }

    @Override
    public UserDto getById(Long id) {
        return dsl.selectFrom(u)
                .where(u.REMOVED.isFalse(), u.ID.eq(id))
                .fetchSingle(UserDto::of);
    }

    @Override
    public Boolean existsById(Long id) {
        return dsl.fetchExists(u, u.ID.eq(id), u.REMOVED.isFalse());
    }

    @Override
    public Boolean existsByEmail(String email) {
        return dsl.fetchExists(u, u.EMAIL.eq(email), u.REMOVED.isFalse());
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
    public UserDto updateById(Consumer<UserRecord> fn, Long id) {
        var record = new UserRecord();
        fn.accept(record);
        return dsl.update(u)
                .set(record)
                .where(u.REMOVED.isFalse(), u.ID.eq(id))
                .returning()
                .fetchSingle(UserDto::of);
    }

    @Override
    public Long removeById(Long id) {
        return (long) dsl.update(u)
                .set(u.REMOVED, true)
                .where(u.REMOVED.isFalse(), u.ID.eq(id))
                .execute();
    }

    @Override
    public UserDto getByEmail(String email) {
        String normalizedEmail = email.trim().toLowerCase();
        return dsl.selectFrom(u)
                .where(u.REMOVED.isFalse(), u.EMAIL.eq(normalizedEmail))
                .fetchSingle(UserDto::of);
    }

    @Override
    public Page<UserDto> getAll(PageRequest pageRequest) {
        Long totalElements = dsl.selectCount()
                .from(u)
                .where(u.REMOVED.isFalse())
                .fetchOne(0, Long.class);

        long count = totalElements != null ? totalElements : 0L;

        List<UserDto> content = dsl.selectFrom(u)
                .where(u.REMOVED.isFalse())
                .limit(pageRequest.getSize())
                .offset(pageRequest.getOffset())
                .fetch(UserDto::of);

        return new Page<>(content, count, pageRequest);
    }

}
