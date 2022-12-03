package org.example.ers.services;

import org.example.ers.data_access_objects.UserDAO;
import org.example.ers.data_access_objects.UserRoleDAO;
import org.example.ers.data_transfer_objects.requests.LoginRequest;
import org.example.ers.models.Principal;
import org.example.ers.models.RoleEnum;
import org.example.ers.models.User;
import org.example.ers.utilities.UtilityMethods;
import org.example.ers.utilities.custom_exceptions.InvalidCredentialsException;

import java.util.Properties;


public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public Principal login(LoginRequest req) throws InvalidCredentialsException {
        System.out.println(UtilityMethods.hashPasswordWithSalt(req.getPassword()));
        User validUser = userDAO.findByUsernameAndPasswordHash(req.getUsername(), UtilityMethods.hashPasswordWithSalt(req.getPassword()));
        if (validUser == null) throw new InvalidCredentialsException("invalid username or password");
        String roleName = validUser.getRoleId();
        return new Principal(validUser.getUserId(), RoleEnum.valueOf(roleName));
    }
}
