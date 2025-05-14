package org.booking_hotel.auth;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.booking_hotel.auth.model.sign_in.SignInRequest;
import org.booking_hotel.auth.model.sign_up.SignUpRequest;
import org.booking_hotel.daos.users.UserDao;
import org.booking_hotel.daos.users.dto.UserDto;
import org.booking_hotel.jooq.model.enums.UserRole;
import org.booking_hotel.jooq.model.tables.records.UserRecord;
import org.booking_hotel.jwt.JwtService;
import org.booking_hotel.jwt.model.JwtModel;
import org.booking_hotel.utils.BusinessException;
import org.booking_hotel.utils.PasswordUtil;
import org.jooq.exception.NoDataFoundException;

import java.util.Set;
import java.util.function.Consumer;

@RequestScoped
@Transactional
public class AuthService {

    @Inject
    UserDao userDao;

    @Inject
    JwtService jwtService;

    public JwtModel signIn(SignInRequest req) throws BusinessException {
        String email = req.email();
        String password = req.password();

        UserDto user;
        try {
            user = userDao.getByEmail(email);
        } catch (NoDataFoundException e) {
            throw new BusinessException(
                    Response.Status.UNAUTHORIZED.getStatusCode(),
                    "auth.signIn.invalidEmail",
                    "Invalid email"
            );
        }

        boolean isPasswordValid = PasswordUtil.checkPassword(password, user.passwordHash());
        if (!isPasswordValid) {
            throw new BusinessException(
                    Response.Status.UNAUTHORIZED.getStatusCode(),
                    "auth.signIn.invalidPassword",
                    "Invalid password"
            );
        }

        String jwt = jwtService.generateJwt(user.email(), Set.of(user.role()));
        return new JwtModel(jwt);
    }

    public JwtModel signUp(SignUpRequest req) throws BusinessException {
        Boolean isExistsByEmail = userDao.existsByEmail(req.email());

        if (isExistsByEmail) {
            throw new BusinessException(
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    "auth.signUp.emailExists",
                    "Email exists"
            );
        }

        Consumer<UserRecord> fn = record -> {
            record.setFirstName(req.firstName());
            record.setLastName(req.lastName());
            record.setEmail(req.email());
            record.setPasswordHash(PasswordUtil.hashPassword(req.password()));
            record.setRole(UserRole.user);
        };

        UserDto createdUser = userDao.insert(fn);

        String jwt = jwtService.generateJwt(createdUser.email(), Set.of(createdUser.role()));
        return new JwtModel(jwt);
    }
}
