package org.example.ers.services;

import org.example.ers.data_access_objects.UserDAO;
import org.example.ers.data_transfer_objects.requests.LoginRequest;
import org.example.ers.data_transfer_objects.requests.UserNew;
import org.example.ers.data_transfer_objects.responses.Principal;
import org.example.ers.models.User;
import org.example.ers.utilities.custom_exceptions.InvalidCredentialsException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }
    public String createUser(UserNew req) {
        String id = UUID.randomUUID().toString();
        User newUser = new User(id,
                            req.getUsername(),
                            req.getEmail(),
                            req.getPassword(),
                            req.getGivenName(),
                            req.getSurname());
        try {
            validateNewUser(newUser);
            this.userDAO.create(newUser);
        } catch (Exception e) {
            // need to set response to a 400 style with reason why.
            System.out.println(e.toString());
            System.out.println("invalid user");
        }
        return "success";
    }

    public Principal login(LoginRequest req) {
        User validUser = userDAO.findByUsername(req.getUsername());
        if (validUser == null) {
            throw new RuntimeException("invalid username");
        }
        System.out.println(validUser.getPassword());
        System.out.println(req.getPassword());
        if (!Objects.equals(validUser.getPassword(), req.getPassword())) {
            throw new RuntimeException("invalid password");
        }
        return new Principal(validUser.getUserId(), validUser.getUsername(), validUser.getRole());
    }

    private void validateNewUser(User newUser) throws InvalidCredentialsException {
        // test for uniqueness
        List<User> allUsers = this.userDAO.findAll();
        for (User curr : allUsers) {
            if (curr.getUsername() == newUser.getUsername()) {
                throw new InvalidCredentialsException("username taken");
            }
            if (curr.getEmail() == newUser.getEmail()) {
                throw new InvalidCredentialsException("email taken");
            }
        }
    }
}
