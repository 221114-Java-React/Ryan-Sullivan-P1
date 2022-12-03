package com.revature.ers.services;

import com.revature.ers.data_access_objects.UserDAO;
import com.revature.ers.data_transfer_objects.requests.LoginRequest;
import com.revature.ers.models.Principal;
import com.revature.ers.models.RoleEnum;
import com.revature.ers.models.User;
import com.revature.ers.utilities.UtilityMethods;
import com.revature.ers.utilities.custom_exceptions.InvalidCredentialsException;


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
