package org.example.ers.services;

// mostly unchanged from trainer-p1
// not much reason to deviate
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.example.ers.data_transfer_objects.responses.Principal;
import org.example.ers.models.UserRole;
import org.example.ers.utilities.JwtConfig;


import java.util.Date;

public class TokenService {
    private JwtConfig jwtConfig;

    public TokenService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String generateToken(Principal principal) {
        long now = System.currentTimeMillis();
        long expiration = now + jwtConfig.getValidForTime();
        Date issueTime = new Date(now);
        Date expirationTime = new Date(expiration);
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(principal.getId())
                .setIssuer("revature ers system")
                .setIssuedAt(issueTime)
                .setExpiration(expirationTime)
                .setSubject(principal.getUsername())
                .claim("role", "default")
                .signWith(jwtConfig.getSigAlg(), jwtConfig.getSigningKey());

        return jwtBuilder.compact();
    }

    public Principal extractRequesterDetails(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtConfig.getSigningKey())
                .parseClaimsJws(token)
                .getBody();
        Principal principal = new Principal(claims.getId(),
                                        claims.getSubject());
        return principal;
    }
}
