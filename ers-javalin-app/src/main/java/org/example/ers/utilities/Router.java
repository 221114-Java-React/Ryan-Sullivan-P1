package org.example.ers.utilities;

import io.javalin.Javalin;
import org.example.ers.handlers.AuthHandler;
import org.example.ers.handlers.TicketHandler;
import org.example.ers.handlers.UserHandler;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Router {
    public static void registerRoutes(Javalin app) {
        UserHandler userHandler = new UserHandler();
        AuthHandler authHandler = new AuthHandler();
        TicketHandler ticketHandler = new TicketHandler();

        app.routes(() -> {
            path("/users", () -> {
                post(userHandler::createUser); // create user
                put(userHandler::updateUser); // update user
                delete(userHandler::deleteUser); //delete user
            });
            path("/login", () -> post(authHandler::login));

            path("/ticket", () -> {
                // for managers
                get(ticketHandler::getAll); // get all tickets
                get("/type", ticketHandler::getByType); // filter by type
                get("/status", ticketHandler::getByStatus); // filter by status
                patch("approve/{id}", ticketHandler::approve); // approve
                patch("deny/{id}", ticketHandler::deny); // deny

                // employee (can view own) or manager (any)
                get("/details", ticketHandler::getDetails); // get ticket details
                get("/user/{username}", ticketHandler::getUsersTickets); // get all users submitted tickets
                post(ticketHandler::submit); // submit new ticket
            });
        });
    }
}
