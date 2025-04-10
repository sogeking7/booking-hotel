package org.todo.users;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.todo.users.dto.UserDto;
import org.todo.users.model.UserSaveRequest;
import org.todo.users.model.UserSaveResponse;
import org.todo.utils.PasswordUtil;

import java.util.List;

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

    public UserSaveResponse createUser(UserSaveRequest req) {

        Boolean isUserEmailExist = userDao.getByEmail(req.email()).isPresent();

        if (isUserEmailExist) {
            // throw exception
        }

        String hashedPassword = PasswordUtil.hashPassword(req.password());

        UserDto createdUser = userDao.insert(record -> {
            record.setFirstName(req.firstName());
            record.setLastName(req.lastName());
            record.setEmail(req.email());
            record.setPasswordHash(hashedPassword);
        });

        return new UserSaveResponse(createdUser.id());
    }
}
