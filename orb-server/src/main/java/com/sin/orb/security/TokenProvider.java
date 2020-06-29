package com.sin.orb.security;

import com.sin.orb.domain.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * This class contains methods for working with json web tokens
 * (such as generating and validating tokens)
 */
@Slf4j
@Service
public class TokenProvider {
    /**
     * Secret key to generate a token
     */
    @Value("${app.auth.tokenSecret}")
    private String tokenSecret;

    /**
     * Creates a token based on the Authentication instance
     *
     * @param authentication Authentication instance that the token will be created from
     * @return created token
     */
    public String createToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return Jwts.builder()
                   .setSubject(Long.toString(user.getId()))
                   .setIssuedAt(new Date())
                   .signWith(SignatureAlgorithm.HS512, tokenSecret)
                   .compact();
    }

    /**
     * Returns the user id passed as subject when creating this token
     *
     * @param token token for identifier
     * @return id of the user who owns the token
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

    /**
     * Checks the validity of the token.
     * If validation is successful, it returns true, otherwise false.
     *
     * @param authToken token to be validated
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}