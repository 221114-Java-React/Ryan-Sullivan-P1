package org.example.ers.services;

import org.example.ers.data_access_objects.UserDAO;
import org.example.ers.data_access_objects.UserRoleDAO;
import org.example.ers.data_transfer_objects.requests.LoginRequest;
import org.example.ers.data_transfer_objects.requests.NewUserRequest;
import org.example.ers.models.Principal;
import org.example.ers.models.RoleEnum;
import org.example.ers.models.User;
import org.example.ers.utilities.UtilityMethods;
import org.example.ers.utilities.custom_exceptions.InvalidCredentialsException;
import org.example.ers.utilities.custom_exceptions.InvalidUserFieldsException;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Properties;


public class UserService {

    private final Properties properties;

    private final UserDAO userDAO;
    private final UserRoleDAO userRoleDAO;

    public UserService() {
        this.userRoleDAO = new UserRoleDAO();
        this.userDAO = new UserDAO();
        this.properties = new Properties();
    }

    public void createUser(NewUserRequest req) throws InvalidUserFieldsException {
        validateNewUserRequest(req);
        User newUser = new User(UtilityMethods.generateId(),
                            req.getUsername(),
                            req.getEmail(),
                            hashPasswordWithSalt(req.getPassword()),
                            req.getGivenName(),
                            req.getSurname(),
                     false,
                            userRoleDAO.getIdFromApiName("EMPLOYEE"));

        this.userDAO.create(newUser);
    }

    public Principal login(LoginRequest req) throws InvalidCredentialsException {
        User validUser = userDAO.findByUsernameAndPasswordHash(req.getUsername(), hashPasswordWithSalt(req.getPassword()));
        if (validUser == null) throw new InvalidCredentialsException("invalid username or password");
        UserRoleDAO roleDAO = new UserRoleDAO();
        String roleName = roleDAO.getApiNameFromId(validUser.getRoleId());
        return new Principal(validUser.getUserId(), RoleEnum.valueOf(roleName));
    }

     // private validation methods and helpers

    private void validateNewUserRequest(NewUserRequest req) throws InvalidUserFieldsException {
        if (!UtilityMethods.validUsername(req.getUsername())) throw new InvalidUserFieldsException("bad or missing username");
        if (!UtilityMethods.validEmail(req.getEmail())) throw new InvalidUserFieldsException("bad or missing email");
        if (!req.getPassword().equals(req.getPasswordTwo())) throw new InvalidUserFieldsException("passwords do not match");
        if (!UtilityMethods.validPassword(req.getPassword())) throw new InvalidUserFieldsException("bad or missing password");
        if (req.getGivenName() == null || req.getGivenName().isEmpty()) throw new InvalidUserFieldsException("given name required");
        if (req.getSurname() == null || req.getSurname().isEmpty()) throw new InvalidUserFieldsException("surname required");

        // test for uniqueness
        if (this.userDAO.usernameTaken(req.getUsername())) throw new InvalidUserFieldsException("username taken");
        if (this.userDAO.emailTaken(req.getEmail())) throw new InvalidUserFieldsException("email taken");
    }

    private String hashPasswordWithSalt(String password) {
        String salted = properties.getProperty("salt") + password;
        return DigestUtils.sha256Hex(salted);
    }
}
