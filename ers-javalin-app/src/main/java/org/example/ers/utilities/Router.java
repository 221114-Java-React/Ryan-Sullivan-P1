package org.example.ers.utilities;

import io.javalin.Javalin;
import org.example.ers.handlers.UserHandler;
import org.example.ers.services.UserService;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Router {
    public static void registerRoutes(Javalin app) {
        UserHandler userHandler = new UserHandler();

        app.routes(() -> {
            path("/users", () -> {
                post(ctx -> {
                    userHandler.createUser(ctx);
                }); // create user
                put(ctx -> {}); // update user
                delete(ctx -> {}); //delete user
            });
            path("/login", () -> {
                post(ctx -> {});
            });
            path("/ticket", () -> {
                // for managers
                get(ctx -> {}); // get all tickets
                get("/type", ctx -> {}); // filter by type
                get("/status", ctx -> {}); // filter by status
                patch("approve/{id}", ctx -> {}); // approve
                patch("deny/{id}", ctx -> {}); // deny

                // employee (can view own) or manager (can view details of any)
                get("/details", ctx -> {}); // get ticket details

                // for employees
                get("/{username}", ctx -> {}); // get all "my" tickets
                post(ctx -> {}); // submit new ticket
            });
        });
    }
}
