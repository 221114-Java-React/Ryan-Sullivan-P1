package org.example.ers.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.example.ers.data_transfer_objects.requests.UserNew;
import org.example.ers.services.UserService;

import java.io.IOException;

public class UserHandler {
    private final ObjectMapper mapper;
    private final UserService service;
    public UserHandler() {
        this.mapper = new ObjectMapper();
        this.service = new UserService();
    }
    public void createUser(Context ctx) throws IOException {
        UserNew userNew = mapper.readValue(ctx.req.getInputStream(), UserNew.class);

        service.createUser(userNew);
        ctx.status(201);
    }
}
