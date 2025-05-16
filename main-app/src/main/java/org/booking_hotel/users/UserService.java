package org.booking_hotel.users;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.booking_hotel.common.Page;
import org.booking_hotel.common.PageRequest;
import org.booking_hotel.daos.users.UserDao;
import org.booking_hotel.daos.users.dto.UserDto;
import org.booking_hotel.jooq.model.tables.records.UserRecord;
import org.booking_hotel.users.model.UserSaveRequest;
import org.booking_hotel.users.model.UserSaveResponse;
import org.booking_hotel.utils.BusinessException;
import org.booking_hotel.utils.PasswordUtil;

import java.util.function.Consumer;

//dependency injection, scope CDI
@RequestScoped
@Transactional
public class UserService {

    @Inject
    UserDao userDao;

    public Page<UserDto> getAllUsers(PageRequest pageRequest) {
        return userDao.getAll(pageRequest);
    }

    public UserDto getUserById(Long id) {
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

    public void deleteUserById(Long id) {
        userDao.removeById(id);
    }
}
