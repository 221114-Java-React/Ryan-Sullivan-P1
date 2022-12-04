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
        UserHandler userHandler = new UserHandler(userService);
        AuthHandler authHandler = new AuthHandler(userService, tokenService, objectMapper);
        TicketHandler ticketHandler = new TicketHandler(ticketService, objectMapper);
        RegistrationHandler registrationHandler = new RegistrationHandler(registrationService, objectMapper);
        // register routes
        app.routes(() -> {
            path("login", () -> post(authHandler::login));

            path("registrations", () -> {
                post(registrationHandler::register);
                // approval or denial of registration by managers only
                get(registrationHandler::getAll, RoleEnum.ADMIN);
                delete("{username}", registrationHandler::delete, RoleEnum.ADMIN);
            });

            // all user management by administrators only
            path("users", () -> {
                post("{username}", userHandler::approveRegistration, RoleEnum.ADMIN);
                put("reset/{username}", userHandler::resetUserPassword, RoleEnum.ADMIN);
                put("deactivate/{username}", userHandler::deactivateUser, RoleEnum.ADMIN);
            });

            path("tickets", () -> {
                // for all employees
                post(ticketHandler::submit, RoleEnum.EMPLOYEE, RoleEnum.MANAGER, RoleEnum.ADMIN);
                get("mine", ticketHandler::getAllMyTickets, RoleEnum.EMPLOYEE, RoleEnum.MANAGER, RoleEnum.ADMIN);
                get("mine/pending", ticketHandler::getMyPendingTickets, RoleEnum.EMPLOYEE, RoleEnum.MANAGER, RoleEnum.ADMIN);
                get("mine/filtered", ticketHandler::getMyFilteredTickets, RoleEnum.EMPLOYEE, RoleEnum.MANAGER, RoleEnum.ADMIN);
                put("mine/{id}", ticketHandler::updateMyTicket, RoleEnum.EMPLOYEE, RoleEnum.MANAGER, RoleEnum.ADMIN);
                delete("mine/{id}", ticketHandler::deleteMyTicket, RoleEnum.EMPLOYEE, RoleEnum.MANAGER, RoleEnum.ADMIN);

                // only for financial managers
                get("status/pending", ticketHandler::getAllPending, RoleEnum.MANAGER);
                get("status/approved-by-me", ticketHandler::getByStatusForManager, RoleEnum.MANAGER);
                get("status/rejected-by-me", ticketHandler::getByStatusForManager, RoleEnum.MANAGER);
                put("approve/{id}", (ctx) -> ticketHandler.resolve(ctx, TicketStatus.APPROVED), RoleEnum.MANAGER);
                put("reject/{id}", (ctx) -> ticketHandler.resolve(ctx, TicketStatus.REJECTED), RoleEnum.MANAGER);
            });
        });

    }
}
