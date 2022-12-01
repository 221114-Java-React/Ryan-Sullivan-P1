package org.example.ers.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import org.example.ers.handlers.AuthHandler;
import org.example.ers.handlers.TicketHandler;
import org.example.ers.handlers.UserHandler;
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
                put(userHandler::updateUser, UserRole.ADMIN); // update user
                delete(userHandler::deleteUser, UserRole.ADMIN); //delete user
            });
            
            path("/login", () -> post(authHandler::login));

            path("/tickets", () -> {
                // for managers
                get(ticketHandler::getAll, UserRole.ADMIN, UserRole.MANAGER); // get all tickets
                get("/type/{type}", ticketHandler::getByType, UserRole.ADMIN, UserRole.MANAGER); // filter by type
                get("/status/{status}", ticketHandler::getByStatus, UserRole.ADMIN, UserRole.MANAGER); // filter by status
                patch("approve/{id}", ticketHandler::approve, UserRole.ADMIN, UserRole.MANAGER); // approve
                patch("deny/{id}", ticketHandler::deny, UserRole.ADMIN, UserRole.MANAGER); // deny

                // employee (can view own) or manager (any)
                get("/details/{id}", ticketHandler::getDetails); // get ticket details
                get("/user/{username}", ticketHandler::getUsersTickets); // get all users submitted tickets
                post(ticketHandler::submit); // submit new ticket
            });
        });


    }
}
