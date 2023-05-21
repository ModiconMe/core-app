package edu.modicon.app.infrastructure.security.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Get config from application.yml
 */
@Getter
@Component
public class JwtConfig {

    @Value("${jwt.sign-key}")
    private String singKey;

    @Value("${jwt.token-prefix}")
    private String tokenPrefix;

    @Value("${jwt.valid-time}")
    private Integer validTime;

    @Value("${jwt.issuer}")
    private String issuer;

    public Date getIssueAt() {
        return new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(validTime));
    }

    public Key getKey() {
        if (singKey.length() < 32)
            throw new RuntimeException("signKey must have length at least 32");
        return Keys.hmacShaKeyFor(singKey.getBytes(StandardCharsets.UTF_8));
    }

}
