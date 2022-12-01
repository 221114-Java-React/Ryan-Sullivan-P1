package org.example.ers.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import io.javalin.http.Context;
import org.example.ers.data_transfer_objects.requests.LoginRequest;
import org.example.ers.models.Principal;
import org.example.ers.services.TokenService;
import org.example.ers.services.UserService;
import org.example.ers.utilities.custom_exceptions.InvalidCredentialsException;

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
        try {
            LoginRequest req = mapper.readValue(ctx.req.getInputStream(), LoginRequest.class);
            try {
                Principal userPrincipal = userService.login(req);
                ctx.json(tokenService.generateToken(userPrincipal));
                ctx.status(202);
            } catch (InvalidCredentialsException e) {
                ctx.status(401).result(e.getMessage());
            }
        } catch (UnrecognizedPropertyException e) {
            ctx.status(400).result(e.getMessage());
        }

    }
}
