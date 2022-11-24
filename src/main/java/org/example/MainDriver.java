package org.example;
import io.javalin.Javalin;
import org.example.utilities.Router;

/**
 * Hello world!
 *
 */
public class MainDriver
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Javalin app = Javalin.create();
        Router.registerRoutes(app);
        app.start(8080);
    }
}
