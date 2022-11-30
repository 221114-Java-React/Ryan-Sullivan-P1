package org.example.ers.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.example.ers.data_transfer_objects.requests.LoginRequest;
import org.example.ers.data_transfer_objects.responses.Principal;
import org.example.ers.services.TokenService;
import org.example.ers.services.UserService;

import java.io.IOException;

public class AuthHandler {
    private UserService userService;
    private TokenService tokenService;
    private ObjectMapper mapper;

    public AuthHandler(UserService userService, TokenService tokenService, ObjectMapper mapper) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.mapper = mapper;
    }

    public void login(Context ctx) throws IOException {
        System.out.println("logging in a user");
        LoginRequest req = mapper.readValue(ctx.req.getInputStream(), LoginRequest.class);

        Principal userPrincipal = userService.login(req);

        String token = tokenService.generateToken(userPrincipal);
        ctx.res.setHeader("authorization", token);
        ctx.json(userPrincipal);
        ctx.status(202);
    }
}
