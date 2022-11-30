package org.example.ers.utilities;

import io.javalin.core.security.RouteRole;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.ers.data_transfer_objects.responses.Principal;
import org.example.ers.services.TokenService;
import org.example.ers.utilities.enums.UserRole;

import java.util.Set;

public class AccessConfiguration {
    public static void configure(Handler handler, Context context, Set<RouteRole> routeRoles) throws Exception {
        JwtConfig jwtConfig = new JwtConfig();
        TokenService tokenService = new TokenService(jwtConfig);

        Principal principal = tokenService.extractUserDetailsFromContext(context);
        if (principal != null)context.attribute("principal", principal);

        if (routeRoles.isEmpty() || routeRoles.contains(principal.getRole())) {
            handler.handle(context);
        } else {
            context.status(401).result("Unauthorized");
        }
    }
}
