package org.todo.users;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.todo.jooq.model.tables.records.UserRecord;
import org.todo.users.dto.UserDto;
import org.todo.users.model.UserSaveRequest;
import org.todo.users.model.UserSaveResponse;
import org.todo.utils.BusinessException;
import org.todo.utils.PasswordUtil;

import java.util.List;
import java.util.function.Consumer;

//dependency injection, scope CDI
@RequestScoped
@Transactional
public class UserService {

    @Inject
    UserDao userDao;

    public List<UserDto> getAllUsers() {
        return userDao.getAll();
    }

    public UserDto getUserById(Integer id) {
        return userDao.getById(id);
    }

    public UserSaveResponse saveUser(UserSaveRequest req) throws BusinessException {
        boolean isUserEmailExist = userDao.existsByEmail(req.email());

        if (isUserEmailExist) {
            throw new BusinessException(
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    "user.save.emailAlreadyExists",
                    "User with email " + req.email() + " already exists"
            );
        }

        String hashedPassword = PasswordUtil.hashPassword(req.password());

        Consumer<UserRecord> fn = record -> {
            record.setFirstName(req.firstName());
            record.setLastName(req.lastName());
            record.setEmail(req.email());
            record.setPasswordHash(hashedPassword);
        };

        UserDto createdUser = req.id() == null ? userDao.insert(fn) : userDao.updateById(fn, req.id());

        return new UserSaveResponse(createdUser.id());
    }

    public void deleteUserById(Integer id) {
        userDao.deleteById(id);
    }
}
