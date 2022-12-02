package org.example.ers.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.example.ers.services.UserService;

public class UserHandler {
    private ObjectMapper mapper;
    private UserService userService;

    public UserHandler(UserService userService, ObjectMapper objectMapper) {
        this.mapper = objectMapper;
        this.userService = userService;
    }

    public void approveRegistration(Context ctx) {

    }

    public void updateUser(Context ctx) {
        System.out.println("update user only admin should be able to do this");
    }

    public void deactivateUser(Context ctx) {

    }
}
