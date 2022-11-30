package org.example.ers.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.HeaderTokenizer;
import io.javalin.Javalin;
import jdk.nashorn.internal.parser.Token;
import org.example.ers.handlers.AuthHandler;
import org.example.ers.handlers.TicketHandler;
import org.example.ers.handlers.UserHandler;
import org.example.ers.services.TokenService;
import org.example.ers.services.UserService;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Router {
    public static void registerRoutes(Javalin app) {
        UserService userService = new UserService();
        TokenService tokenService = new TokenService(new JwtConfig());
        ObjectMapper objectMapper = new ObjectMapper();


        UserHandler userHandler = new UserHandler(userService, objectMapper);
        AuthHandler authHandler = new AuthHandler(userService, tokenService, objectMapper);
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
