package org.example.ers.utilities;

import io.javalin.core.security.RouteRole;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.ers.models.Principal;
import org.example.ers.services.TokenService;

import java.util.Set;

public class AccessConfiguration {
    public static void configure(Handler handler,
                                 Context context,
                                 Set<RouteRole> routeRoles,
                                 TokenService tokenService
    ) throws Exception
    {
        Principal principal = tokenService.extractUserDetailsFromContext(context);
        context.attribute("principal", principal);

        if (routeRoles.isEmpty() || routeRoles.contains(principal.getRole())) {
            handler.handle(context);
        } else {
            context.status(401).result("Unauthorized");
        }
    }
}
