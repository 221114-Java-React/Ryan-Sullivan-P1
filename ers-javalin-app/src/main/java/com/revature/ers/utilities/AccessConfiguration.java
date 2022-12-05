package com.revature.ers.utilities;

import com.revature.ers.data_access_objects.UserDAO;
import com.revature.ers.models.User;
import io.javalin.core.security.RouteRole;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.jsonwebtoken.JwtException;
import com.revature.ers.models.Principal;
import com.revature.ers.services.TokenService;

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
            } catch (JwtException e) {
                context.status(400).result(e.getMessage());
                return;
            }
        }

        if (routeRoles.isEmpty() || (principal != null && routeRoles.contains(principal.getRole()))) {
            if (principal != null) {
                UserDAO userDAO = new UserDAO();
                User user = userDAO.findById(principal.getId());
                if (user == null || !user.isActive()) {
                    context.status(401).result("unauthorized");
                    return;
                }
            }
            handler.handle(context);
        } else {
            context.status(401).result("Unauthorized");
        }
    }
}
