package com.revature.ers.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.revature.ers.services.UserService;
import io.javalin.http.Context;
import com.revature.ers.data_transfer_objects.requests.LoginRequest;
import com.revature.ers.models.Principal;
import com.revature.ers.services.TokenService;
import com.revature.ers.utilities.custom_exceptions.InvalidCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AuthHandler {
    private final static Logger logger = LoggerFactory.getLogger(AuthHandler.class);
    private final UserService userService;
    private final TokenService tokenService;
    private final ObjectMapper mapper;

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
                logger.info("user logged in");
            } catch (InvalidCredentialsException e) {
                logger.info("bad credentials sent for login");
                ctx.status(401).result(e.getMessage());
            }
        } catch (UnrecognizedPropertyException e) {
            logger.info("bad credentials sent for login");
            ctx.status(400).result(e.getMessage());
        }
    }
}
