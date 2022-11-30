package org.example.ers;
import io.javalin.Javalin;

import org.example.ers.utilities.AccessConfiguration;
import org.example.ers.utilities.Router;

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
