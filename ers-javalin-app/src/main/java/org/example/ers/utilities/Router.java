package org.example.ers.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import org.example.ers.handlers.AuthHandler;
import org.example.ers.handlers.TicketHandler;
import org.example.ers.handlers.UserHandler;
import org.example.ers.models.RoleEnum;
import org.example.ers.models.TicketStatus;
import org.example.ers.services.TicketService;
import org.example.ers.services.TokenService;
import org.example.ers.services.UserService;
import org.example.ers.models.UserRole;


import static io.javalin.apibuilder.ApiBuilder.*;

public class Router {

    public static void registerRoutes(Javalin app, TokenService tokenService) {
        // services
        TicketService ticketService = new TicketService();
        UserService userService = new UserService();
        // mapper
        ObjectMapper objectMapper = new ObjectMapper();
        // handlers
        UserHandler userHandler = new UserHandler(userService, objectMapper);
        AuthHandler authHandler = new AuthHandler(userService, tokenService, objectMapper);
        TicketHandler ticketHandler = new TicketHandler(ticketService, objectMapper);
        // register routes
        app.routes(() -> {
            path("/users", () -> {
                post(userHandler::createUser); // create user
                put("/{id}", userHandler::updateUser, RoleEnum.ADMIN); // update user
                delete("/{id}", userHandler::deleteUser, RoleEnum.ADMIN); //delete user
            });
            
            path("/login", () -> post(authHandler::login));

            path("/tickets", () -> {
                // for managers
                get(ticketHandler::getAll, RoleEnum.MANAGER); // get all tickets
                get("/type/{type}", ticketHandler::getByType, RoleEnum.MANAGER); // filter by type
                get("/status/{status}", ticketHandler::getPending, RoleEnum.MANAGER); // filter by status
                put("approve/{id}", (ctx) -> ticketHandler.resolve(ctx, TicketStatus.APPROVED), RoleEnum.MANAGER); // approve
                put("reject/{id}", (ctx) -> ticketHandler.resolve(ctx, TicketStatus.REJECTED), RoleEnum.MANAGER); // deny
                get("/user/{username}", ticketHandler::getUsersTickets); // get all users submitted tickets
                get("/details/{id}", ticketHandler::getDetails); // get ticket details
                post(ticketHandler::submit); // submit new ticket
            });
        });

    }
}
