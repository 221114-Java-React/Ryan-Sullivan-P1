package org.example.ers.services;

import org.example.ers.data_access_objects.UserDAO;
import org.example.ers.data_transfer_objects.requests.UserNew;
import org.example.ers.models.User;

import java.util.List;
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

    private void validateNewUser(User newUser) throws Exception {
        // test for uniqueness
        List<User> allUsers = this.userDAO.findAll();
        for (User curr : allUsers) {
            if (curr.getUsername() == newUser.getUsername()) {
                throw new Exception("username taken");
            }
            if (curr.getEmail() == newUser.getEmail()) {
                throw new Exception("email taken");
            }
        }
    }
}
