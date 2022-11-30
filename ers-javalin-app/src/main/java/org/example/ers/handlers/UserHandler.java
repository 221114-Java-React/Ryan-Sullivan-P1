package org.example.ers.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.example.ers.data_transfer_objects.requests.UserNew;
import org.example.ers.services.UserService;

import java.io.IOException;

public class UserHandler {
    private final ObjectMapper mapper;
    private final UserService userService;

    public UserHandler(UserService userService, ObjectMapper objectMapper) {
        this.mapper = new ObjectMapper();
        this.userService = userService;
    }
    public void createUser(Context ctx) throws IOException {
        UserNew userNew = mapper.readValue(ctx.req.getInputStream(), UserNew.class);

        userService.createUser(userNew);
        ctx.status(201);
    }

    public void updateUser(Context ctx) {
        System.out.println("update user only admin should be able to do this");
    }

    public void deleteUser(Context ctx) {

    }
}
