package com.vnpt.config.jwt;

import com.vnpt.service.dto.AccountDetail;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class TokenProviderJWT {
    private final String JWT_SECRET = "SECRETKEYFRAMEWORKSITHS2023SECRETKEYFRAMEWORKSITHS2023SECRETKEYFRAMEWORKSITHS2023SECRETKEYFRAMEWORKSITHS2023SECRETKEYFRAMEWORKSITHS2023";

    private final long JWT_EXPIRATION = 604800000L;

    public String generateTokenCustomer(AccountDetail accountDetail) {
        var now = new Date();
        var expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
            .setSubject(accountDetail.getUsername())
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
            .compact();
    }

    public String getUsernameFromJWT(String token) {
        var claims = Jwts.parser()
            .setSigningKey(JWT_SECRET)
            .parseClaimsJws(token)
            .getBody();

        return claims.getSubject();
    }


    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);

            return true;
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
