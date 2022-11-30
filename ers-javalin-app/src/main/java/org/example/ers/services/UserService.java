package org.example.ers.services;

import org.example.ers.data_access_objects.UserDAO;
import org.example.ers.data_transfer_objects.requests.LoginRequest;
import org.example.ers.data_transfer_objects.requests.UserNew;
import org.example.ers.data_transfer_objects.responses.Principal;
import org.example.ers.models.User;
import org.example.ers.utilities.custom_exceptions.InvalidCredentialsException;
import org.example.ers.utilities.custom_exceptions.InvalidUserFieldsException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }
    public void createUser(UserNew req) throws InvalidUserFieldsException {
        validateNewUserRequest(req);
        String id = UUID.randomUUID().toString();
        User newUser = new User(id,
                            req.getUsername(),
                            req.getEmail(),
                            req.getPassword(),
                            req.getGivenName(),
                            req.getSurname());

        this.userDAO.create(newUser);
    }

    public Principal login(LoginRequest req) throws InvalidCredentialsException {
        User validUser = userDAO.findByUsernameAndPassword(req.getUsername(), req.getPassword());
        if (validUser == null) throw new InvalidCredentialsException("invalid username or password");
        return new Principal(validUser.getUserId(), validUser.getUsername(), validUser.getRole());
    }

    private void validateNewUserRequest(UserNew req) throws InvalidUserFieldsException {
        if (req.getUsername() == null || req.getUsername().length() < 1) throw new InvalidUserFieldsException("bad or missing username");
        if (req.getEmail() == null || req.getEmail().length() < 5) throw new InvalidUserFieldsException("bad or missing email");
        if (req.getPassword() == null || req.getPassword().length() < 5) throw new InvalidUserFieldsException("bad or missing password");
        if (req.getGivenName() == null || req.getGivenName().isEmpty()) throw new InvalidUserFieldsException("given name required");
        if (req.getSurname() == null || req.getSurname().isEmpty()) throw new InvalidUserFieldsException("surname required");

        // test for uniqueness
        if (this.userDAO.usernameTaken(req.getUsername())) throw new InvalidUserFieldsException("username taken");
        if (this.userDAO.emailTaken(req.getEmail())) throw new InvalidUserFieldsException("email taken");
    }
}
