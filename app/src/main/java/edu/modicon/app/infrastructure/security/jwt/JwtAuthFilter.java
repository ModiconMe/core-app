package edu.modicon.app.infrastructure.security.jwt;

import edu.modicon.app.infrastructure.security.AuthenticationProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final JwtConfig jwtConfig;
    private final AuthenticationProvider authenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION)) // get auth header
                .filter(authHeader -> authHeader.startsWith(jwtConfig.getTokenPrefix())) // search for bearer token
                .map(authHeader -> authHeader.substring(jwtConfig.getTokenPrefix().length())) // remove token prefix
                .filter(jwtUtils::isTokenValid) // check that token is not expired and username (email) is exists
                .map(jwtUtils::extractUsername) // get username from token
                .map(authenticationProvider::getAuthentication) // authenticate user -> get user from db by username -> set username and authorities to authentication
                .ifPresent(SecurityContextHolder.getContext()::setAuthentication); // set authentication token to context
        chain.doFilter(request, response);
    }

}
