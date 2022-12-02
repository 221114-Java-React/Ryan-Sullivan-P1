package org.example.ers.utilities;

import io.javalin.core.security.RouteRole;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.jsonwebtoken.ExpiredJwtException;
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
        String token = context.req.getHeader("authorization");
        Principal principal = null;
        if (token != null) {
            try {
                principal = tokenService.extractUserDetailsFromToken(token);
                context.attribute("principal", principal);
            } catch (ExpiredJwtException e){
                context.status(400).result(e.getMessage());
                return;
            }

        }

        if (routeRoles.isEmpty() || (principal != null && routeRoles.contains(principal.getRole()))) {
            handler.handle(context);
        } else {
            context.status(401).result("Unauthorized");
        }
    }
}
