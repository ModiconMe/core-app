package edu.modicon.app.infrastructure.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JwtUtils {

    private final UserDetailsService userDetailsService;
    private final JwtConfig jwtConfig;

    /**
     * Generate jwt token after authentication
     * @param userDetails user credentials (login, password, authorities etc...)
     * @return jwt token as string
     */
    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuer(jwtConfig.getIssuer())
                .claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(jwtConfig.getIssueAt())
                .signWith(jwtConfig.getKey()).compact();
    }

    /**
     * Check that token is not expired (application.yml set 24 hours) and token user is equals to authentication user
     * @param token jwt token
     */
    public boolean isTokenValid(String token) {
        boolean expired = isTokenExpired(token);
        Optional<UserDetails> userDetails = Optional.ofNullable(
                userDetailsService.loadUserByUsername(extractUsername(token)));
        return (userDetails.isPresent() && !expired);
    }

    /**
     * Extract token owner username from jwt token
     * @param token jwt token
     * @return token owner username
     */
    public String extractUsername(String token) {
        Claims claims = extractClaims(token);
        return claims.getSubject();
    }

    /**
     * Check that token is not expired (application.yml set 24 hours)
     * @param token jwt token
     */
    public boolean isTokenExpired(String token) {
        Claims claims = extractClaims(token);
        Instant now = Instant.now();
        Date exp = claims.getExpiration();
        return exp.before(Date.from(now));
    }

    /**
     * Extract encrypted information about token (owner, who issue, when issued and etc.)
     * @param token jwt token
     */
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtConfig.getKey()).build().parseClaimsJws(token).getBody();
    }

}
