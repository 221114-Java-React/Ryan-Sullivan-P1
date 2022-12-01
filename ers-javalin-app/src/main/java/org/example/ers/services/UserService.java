package org.example.ers.services;

import org.example.ers.data_access_objects.UserDAO;
import org.example.ers.data_transfer_objects.requests.LoginRequest;
import org.example.ers.data_transfer_objects.requests.UserNew;
import org.example.ers.models.Principal;
import org.example.ers.models.User;
import org.example.ers.models.UserRole;
import org.example.ers.utilities.UtilityMethods;
import org.example.ers.utilities.custom_exceptions.InvalidCredentialsException;
import org.example.ers.utilities.custom_exceptions.InvalidUserFieldsException;


public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public void createUser(UserNew req) throws InvalidUserFieldsException {
        validateNewUserRequest(req);
        User newUser = new User(UtilityMethods.generateId(),
                            req.getUsername(),
                            req.getEmail(),
                            req.getPassword(),
                            req.getGivenName(),
                            req.getSurname(),
                            false,
                            UserRole.EMPLOYEE);

        this.userDAO.create(newUser);
    }

    public Principal login(LoginRequest req) throws InvalidCredentialsException {
        User validUser = userDAO.findByUsernameAndPassword(req.getUsername(), req.getPassword());
        if (validUser == null) throw new InvalidCredentialsException("invalid username or password");
        return new Principal(validUser.getUserId(), validUser.getUsername(), validUser.getRole());
    }

     // private validation methods and helpers

    private void validateNewUserRequest(UserNew req) throws InvalidUserFieldsException {
        if (!UtilityMethods.validUsername(req.getUsername())) throw new InvalidUserFieldsException("bad or missing username");
        if (!UtilityMethods.validEmail(req.getEmail())) throw new InvalidUserFieldsException("bad or missing email");
        if (!UtilityMethods.validPassword(req.getPassword())) throw new InvalidUserFieldsException("bad or missing password");
        if (req.getGivenName() == null || req.getGivenName().isEmpty()) throw new InvalidUserFieldsException("given name required");
        if (req.getSurname() == null || req.getSurname().isEmpty()) throw new InvalidUserFieldsException("surname required");

        // test for uniqueness
        if (this.userDAO.usernameTaken(req.getUsername())) throw new InvalidUserFieldsException("username taken");
        if (this.userDAO.emailTaken(req.getEmail())) throw new InvalidUserFieldsException("email taken");
    }
}
