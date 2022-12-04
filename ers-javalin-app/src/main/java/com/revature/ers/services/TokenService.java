package com.revature.ers.services;

import com.revature.ers.data_transfer_objects.responses.TokenResponse;
import com.revature.ers.models.Principal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import com.revature.ers.models.RoleEnum;
import com.revature.ers.utilities.JwtConfig;

import java.util.Date;

public class TokenService {
    private final JwtConfig jwtConfig;

    public TokenService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public TokenResponse generateToken(Principal principal) {
        long now = System.currentTimeMillis();
        long expiration = now + jwtConfig.getValidForTime();
        Date issueTime = new Date(now);
        Date expirationTime = new Date(expiration);
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(principal.getId())
                .setIssuer("revature ers system")
                .setIssuedAt(issueTime)
//                .setExpiration(expirationTime)
                .claim("role", principal.getRole())
                .signWith(jwtConfig.getSigAlg(), jwtConfig.getSigningKey());

        return new TokenResponse(jwtBuilder.compact());
    }

    public Principal extractUserDetailsFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSigningKey())
                    .parseClaimsJws(token)
                    .getBody();

            return new Principal(claims.getId(), RoleEnum.valueOf(claims.get("role", String.class)));
        } catch (ExpiredJwtException e) {
            return null;
        }
    }
}
