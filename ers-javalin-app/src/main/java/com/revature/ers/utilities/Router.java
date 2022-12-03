package com.revature.ers.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.handlers.AuthHandler;
import com.revature.ers.handlers.RegistrationHandler;
import com.revature.ers.handlers.TicketHandler;
import com.revature.ers.handlers.UserHandler;
import com.revature.ers.models.TicketStatus;
import com.revature.ers.services.RegistrationService;
import com.revature.ers.services.TicketService;
import com.revature.ers.services.UserService;
import io.javalin.Javalin;
import com.revature.ers.models.RoleEnum;
import com.revature.ers.services.TokenService;


import static io.javalin.apibuilder.ApiBuilder.*;

public class Router {

    public static void registerRoutes(Javalin app, TokenService tokenService) {
        // services
        TicketService ticketService = new TicketService();
        UserService userService = new UserService();
        RegistrationService registrationService = new RegistrationService();
        // mapper
        ObjectMapper objectMapper = new ObjectMapper();
        // handlers
        UserHandler userHandler = new UserHandler(userService, objectMapper);
        AuthHandler authHandler = new AuthHandler(userService, tokenService, objectMapper);
        TicketHandler ticketHandler = new TicketHandler(ticketService, objectMapper);
        RegistrationHandler registrationHandler = new RegistrationHandler(registrationService, objectMapper);
        // register routes
        app.routes(() -> {
            path("registrations", () -> {
                post(registrationHandler::register);
                get(registrationHandler::getAll, RoleEnum.ADMIN);
                get("{username}", registrationHandler::getByUsername, RoleEnum.ADMIN);
                delete("{username}", registrationHandler::delete, RoleEnum.ADMIN);
            });

            path("users", () -> {
                post("{username}", userHandler::approveRegistration, RoleEnum.ADMIN);
                put("{username}", userHandler::updateUser, RoleEnum.ADMIN);
            });

            path("login", () -> post(authHandler::login));

            path("tickets", () -> {
                // for managers
                get(ticketHandler::getAll, RoleEnum.MANAGER); // get all tickets
                put("approve/{id}", (ctx) -> ticketHandler.resolve(ctx, TicketStatus.APPROVED), RoleEnum.MANAGER);
                put("reject/{id}", (ctx) -> ticketHandler.resolve(ctx, TicketStatus.REJECTED), RoleEnum.MANAGER);

                // for employees
                post(ticketHandler::submit, RoleEnum.EMPLOYEE, RoleEnum.MANAGER, RoleEnum.ADMIN); // submit new ticket
                get("mine", ticketHandler::getUsersTickets, RoleEnum.EMPLOYEE, RoleEnum.MANAGER, RoleEnum.ADMIN);
                put("mine/{id}", (ctx) -> ticketHandler.resolve(ctx, TicketStatus.REJECTED), RoleEnum.MANAGER);
                delete("mine/{id}", (ctx) -> ticketHandler.resolve(ctx, TicketStatus.REJECTED), RoleEnum.MANAGER);
            });
        });

    }
}
