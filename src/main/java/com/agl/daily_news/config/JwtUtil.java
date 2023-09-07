package com.agl.daily_news.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtil {
    private final String JWT_SECRET_KEY = "dailynewsbca";
    private final Long JWT_EXPIRATION_MS = 1 * 60 * 60 * 1000L;

    public String createToken(String email) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET_KEY);
            Instant issuedAt = Instant.now();
            Date expirationDate = Date.from(issuedAt.plusMillis(JWT_EXPIRATION_MS));

            String token = JWT.create()
                    .withIssuer("fazzbca")
                    .withSubject("auth")
                    .withIssuedAt(Date.from(issuedAt))
                    .withExpiresAt(expirationDate)
                    .withClaim("email", email)
                    .sign(algorithm);

            return token;
        } catch (Exception e) {
            throw new RuntimeException("Unable to generate JWT token");
        }
    }

    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET_KEY);
            JWT.require(algorithm)
                    .withIssuer("fazzbca")
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getClaim("email").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
