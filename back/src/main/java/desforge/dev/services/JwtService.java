package desforge.dev.services;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

import javax.crypto.SecretKey;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService implements ITokenService{

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    private SecretKey secretKey;
    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generateToken(String subject) {
        validateInputs(subject);

        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + jwtExpirationMs * 1000L);

        return Jwts.builder()
                .subject(subject)
                .audience()
                .and()
                .issuedAt(issuedAt)
                .notBefore(issuedAt)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    @Override
    public boolean validateJwtToken(String token) throws SecurityException, MalformedJwtException,
            ExpiredJwtException, UnsupportedJwtException, IllegalArgumentException {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    private void validateInputs(String subject) {
        Objects.requireNonNull(subject, "Token subject must not be null");
        Objects.requireNonNull(secretKey, "Secret key must not be null");
    }
}
