package com.revature.ers.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.models.Principal;
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
        String username = ctx.pathParam("username");
        String newPassword = this.userService.resetPassword(username);
        ctx.status(200).result(newPassword);
    }

    public void updateUserIsActive(Context ctx, boolean isActive) {
        String username = ctx.pathParam("username");
        this.userService.updateUserIsActive(username, isActive);
    }
}
