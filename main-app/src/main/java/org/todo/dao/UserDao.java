package org.todo.dao;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.todo.dto.UserDto;
import org.todo.jooq.model.tables.Users;
import org.todo.jooq.model.tables.records.UserRecord;
import org.todo.util.PasswordUtil;

import java.util.List;

@Singleton
public class UserDao {

    @Inject
    DSLContext dsl;

    public List<UserDto> getUsersDto() {
        Result<UserRecord> result = dsl.selectFrom(Users.USERS).fetch();

        return result.stream().map(r -> new UserDto(
                        r.getId().longValue(),
                        r.getFirstName(),
                        r.getLastName(),
                        r.getEmail(),
                        r.getCreatedAt().toString()
                ))
                .toList();
    }

    public UserDto getUserById(int id) {
        Result<UserRecord> result = dsl.selectFrom(Users.USERS).where(Users.USERS.ID.eq(id)).fetch();

        if (!result.isEmpty()) {
            UserRecord userRecord = result.getFirst();
            return new UserDto(
                    userRecord.getId().longValue(),
                    userRecord.getFirstName(),
                    userRecord.getLastName(),
                    userRecord.getEmail(),
                    userRecord.getCreatedAt().toString()
            );
        }
        return null;
    }

    public UserDto getUserByEmail(String email) {
        Result<UserRecord> result = dsl.selectFrom(Users.USERS).where(Users.USERS.EMAIL.eq(email)).fetch();

        if (!result.isEmpty()) {
            UserRecord userRecord = result.getFirst();
            return new UserDto(
                    userRecord.getId().longValue(),
                    userRecord.getFirstName(),
                    userRecord.getLastName(),
                    userRecord.getEmail(),
                    userRecord.getCreatedAt().toString()
            );
        }
        return null;
    }

    public UserDto createUser(UserDto userDto, String password) {
        String hashedPassword = PasswordUtil.hashPassword(password);
        UserRecord userRecord = dsl.insertInto(Users.USERS,
                        Users.USERS.FIRST_NAME,
                        Users.USERS.LAST_NAME,
                        Users.USERS.EMAIL,
                        Users.USERS.PASSWORD_HASH)
                .values(userDto.firstName, userDto.lastName, userDto.email, hashedPassword)  // Provide hashed password here
                .returning()
                .fetchOne();

        if (userRecord != null) {
            return new UserDto(
                    userRecord.getId().longValue(),
                    userRecord.getFirstName(),
                    userRecord.getLastName(),
                    userRecord.getEmail(),
                    userRecord.getCreatedAt().toString()
            );
        }

        return null;
    }

}
