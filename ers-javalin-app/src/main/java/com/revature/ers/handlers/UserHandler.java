package com.revature.ers.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.services.UserService;
import io.javalin.http.Context;

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
