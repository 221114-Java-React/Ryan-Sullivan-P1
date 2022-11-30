package org.example.ers;
import io.javalin.Javalin;

import org.example.ers.services.TokenService;
import org.example.ers.utilities.JwtConfig;
import org.example.ers.utilities.Router;
import org.example.ers.utilities.enums.UserRole;

/**
 * Hello world!
 *
 */
public class MainDriver
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World! I am the Employee Reimbursement System API!" );
        JwtConfig jwtConfig = new JwtConfig();
        TokenService tokenService = new TokenService(jwtConfig);

        Javalin app = Javalin.create(config -> {
            config.accessManager(((handler, context, routeRoles) -> {
                UserRole role = tokenService.extractRoleFromContext(context);
                System.out.println(role);
                if (routeRoles.contains(role)) {
                    handler.handle(context);
                } else {
                    context.status(401).result("Unauthorized");
                }
            }));
        });
        Router.registerRoutes(app);
        app.start(8080);
    }
}
