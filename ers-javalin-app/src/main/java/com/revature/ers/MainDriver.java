package com.revature.ers;
import com.revature.ers.utilities.AccessConfiguration;
import io.javalin.Javalin;

import com.revature.ers.services.TokenService;
import com.revature.ers.utilities.JwtConfig;
import com.revature.ers.utilities.Router;

/**
 * Hello world!
 *
 */
public class MainDriver
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World! I am the Employee Reimbursement System API!" );
        TokenService tokenService = new TokenService(new JwtConfig());

        Javalin app = Javalin.create(config -> {
            config.accessManager((handler, ctx, routeRoles) -> {
                AccessConfiguration.configure(handler, ctx, routeRoles, tokenService);
            });
        });

        Router.registerRoutes(app, tokenService);
        app.start(8080);
    }
}
