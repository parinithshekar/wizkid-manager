package dev.owow.wizkidmanager2000.security.jwt;

import dev.owow.wizkidmanager2000.security.UserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTOperations {

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Value("${jwt.issuer}")
    private String JWT_ISSUER;

    @Value(("${jwt.expiration-hours}"))
    private Integer JWT_EXPIRATION_HOURS;

    private SecretKey jwtKey;

    @PostConstruct
    private void init() {
        jwtKey = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractIssuer(String token) {
        return extractClaim(token, Claims::getIssuer);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(Date.from(Instant.now()));
    }

    public String createToken(UserDetails userDetails) {
        // No custom claims for now
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(JWT_ISSUER)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(JWT_EXPIRATION_HOURS, ChronoUnit.HOURS)))
                .signWith(jwtKey)
                .compact();
    }

    public Boolean isValidToken(String token, UserDetails userDetails) {
        return (
                extractUsername(token).equals(userDetails.getUsername()) &&
                        extractIssuer(token).equals(JWT_ISSUER) &&
                        !isTokenExpired(token)
        );
    }
}
