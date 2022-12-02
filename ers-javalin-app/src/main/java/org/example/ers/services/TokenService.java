package org.example.ers.services;

import io.javalin.http.Context;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.example.ers.models.Principal;
import org.example.ers.data_transfer_objects.responses.Token;
import org.example.ers.models.RoleEnum;
import org.example.ers.utilities.JwtConfig;

import java.util.Date;

public class TokenService {
    private JwtConfig jwtConfig;

    public TokenService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public Token generateToken(Principal principal) {
        long now = System.currentTimeMillis();
        long expiration = now + jwtConfig.getValidForTime();
        Date issueTime = new Date(now);
        Date expirationTime = new Date(expiration);
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(principal.getId())
                .setIssuer("revature ers system")
                .setIssuedAt(issueTime)
                .setExpiration(expirationTime)
                .claim("role", principal.getRole())
                .signWith(jwtConfig.getSigAlg(), jwtConfig.getSigningKey());

        return new Token(jwtBuilder.compact());
    }

    public Principal extractUserDetailsFromContext(Context ctx) {
        String token = ctx.req.getHeader("authorization");

        if (token == null || token.isEmpty()) {
            return null;
        }
        Claims claims = Jwts.parser()
                .setSigningKey(jwtConfig.getSigningKey())
                .parseClaimsJws(token)
                .getBody();

        return new Principal(claims.getId(), RoleEnum.valueOf(claims.get("role", String.class)));
    }
}
