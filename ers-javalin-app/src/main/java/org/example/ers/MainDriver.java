package org.example.ers;
import io.javalin.Javalin;

import org.example.ers.data_transfer_objects.responses.Principal;
import org.example.ers.services.TokenService;
import org.example.ers.utilities.AccessConfiguration;
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

        Javalin app = Javalin.create(config -> {
            config.accessManager(AccessConfiguration::configure);
        });

        Router.registerRoutes(app);
        app.start(8080);
    }
}
