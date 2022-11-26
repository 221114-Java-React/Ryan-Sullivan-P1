package org.example.ers.services;

import org.example.ers.data_access_objects.UserDAO;
import org.example.ers.data_transfer_objects.requests.UserNew;
import org.example.ers.models.User;

import java.util.UUID;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }
    public void createUser(UserNew req) {
        String id = UUID.randomUUID().toString();
        User user = new User(id,
                            req.getUsername(),
                            req.getEmail(),
                            req.getPassword(),
                            req.getGivenName(),
                            req.getSurname());
        this.userDAO.create(user);
    }
}
