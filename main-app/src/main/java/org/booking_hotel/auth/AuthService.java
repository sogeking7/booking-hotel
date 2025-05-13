package org.booking_hotel.auth;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.booking_hotel.auth.model.sign_in.SignInRequest;
import org.booking_hotel.daos.users.UserDao;
import org.booking_hotel.daos.users.dto.UserDto;
import org.booking_hotel.jwt.JwtService;
import org.booking_hotel.jwt.model.JwtModel;
import org.booking_hotel.utils.BusinessException;
import org.booking_hotel.utils.PasswordUtil;
import org.jooq.exception.NoDataFoundException;

import java.util.Set;

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
}
