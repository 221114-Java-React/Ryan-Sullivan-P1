package org.example.utilities;

import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;

public class Router {
    public static void registerRoutes(Javalin app) {
        app.routes(() -> {
            path("/users", () -> {
                post(ctx -> {}); // create user
                put(ctx -> {}); // update user
                delete(ctx -> {}); //delete user
            });
            path("/login", () -> {
                post(ctx -> {});
            });
            path("/reimbursement", () -> {
                // for managers
                get(ctx -> {}); // get all reimbursements
                get("/type", ctx -> {}); // filter by type
                get("/status", ctx -> {}); // filter by status
                patch("approve/{id}", ctx -> {}); // approve
                patch("deny/{id}", ctx -> {}); // deny

                // employee (can view own) or manager (can view details of any)
                get("/details", ctx -> {}); // get reimbursement details

                // for employees
                get("/{username}", ctx -> {}); // get all "my" reimbursements
                post(ctx -> {}); // submit new reimbursement
            });
        });
    }
}
