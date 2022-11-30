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
        String newId = UUID.randomUUID().toString();
        User newUser = new User(newId,
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

     // private validation methods and helpers

    private void validateNewUserRequest(UserNew req) throws InvalidUserFieldsException {
        if (validUsername(req.getUsername())) throw new InvalidUserFieldsException("bad or missing username");
        if (validEmail(req.getEmail())) throw new InvalidUserFieldsException("bad or missing email");
        if (validPassword(req.getPassword())) throw new InvalidUserFieldsException("bad or missing password");
        if (req.getGivenName() == null || req.getGivenName().isEmpty()) throw new InvalidUserFieldsException("given name required");
        if (req.getSurname() == null || req.getSurname().isEmpty()) throw new InvalidUserFieldsException("surname required");

        // test for uniqueness
        if (this.userDAO.usernameTaken(req.getUsername())) throw new InvalidUserFieldsException("username taken");
        if (this.userDAO.emailTaken(req.getEmail())) throw new InvalidUserFieldsException("email taken");
    }

    private boolean validUsername(String username) {
    //        https://stackoverflow.com/questions/12018245/regular-expression-to-validate-username
    //        Only contains alphanumeric characters, underscore and dot.
    //        Underscore and dot can't be at the end or start of a username (e.g _username / username_ / .username / username.).
    //        Underscore and dot can't be next to each other (e.g. user_.name).
    //        Underscore or dot can't be used multiple times in a row (e.g. user__name / user..name).
    //        Number of characters between 8 and 20.
        return username != null && username.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
    }

    private boolean validEmail(String email) {
        // https://emailregex.com/
        return email != null &&
                email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    }

    private boolean validPassword(String password) {
        // https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a
        // Minimum eight characters, at least one letter and one number:

        return password != null && password.matches("\"^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$\"");
    }
}
