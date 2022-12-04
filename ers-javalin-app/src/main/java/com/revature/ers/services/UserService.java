package com.revature.ers.services;

import com.revature.ers.data_access_objects.RegistrationDAO;
import com.revature.ers.data_access_objects.UserDAO;
import com.revature.ers.data_transfer_objects.requests.LoginRequest;
import com.revature.ers.models.Principal;
import com.revature.ers.models.Registration;
import com.revature.ers.models.RoleEnum;
import com.revature.ers.models.User;
import com.revature.ers.utilities.UtilityMethods;
import com.revature.ers.utilities.custom_exceptions.InvalidCredentialsException;


public class UserService {

    private final UserDAO userDAO;
    private final RegistrationDAO registrationDAO;

    public UserService() {
        this.userDAO = new UserDAO();
        this.registrationDAO = new RegistrationDAO();
    }

    public Principal login(LoginRequest req) throws InvalidCredentialsException {
        User validUser = userDAO.findByUsernameAndPasswordHash(req.getUsername(), UtilityMethods.hashPasswordWithSalt(req.getPassword()));
        if (validUser == null) throw new InvalidCredentialsException("invalid username or password");
        String roleName = validUser.getRoleId();
        return new Principal(validUser.getUserId(), RoleEnum.valueOf(roleName));
    }

    public void approve(String username) {
        Registration registration = registrationDAO.findByUsername(username);
        userDAO.createFromRegistration(registration);
        registrationDAO.delete(registration.getRequestId());
    }
}
