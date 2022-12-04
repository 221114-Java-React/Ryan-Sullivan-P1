package com.revature.ers.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.services.UserService;
import io.javalin.http.Context;

public class UserHandler {
    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public void approveRegistration(Context ctx) {
        String username = ctx.pathParam("username");
        this.userService.approve(username);
    }

    public void resetUserPassword(Context ctx) {
        System.out.println("update user only admin should be able to do this");
    }

    public void deactivateUser(Context ctx) {
        System.out.println("update user only admin should be able to do this");
    }
}
