package com.payal.eventra.security;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.payal.eventra.enums.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTH_ERROR_ATTRIBUTE = "jwt.auth.error";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith(BEARER_PREFIX)) {
            String token = header.substring(BEARER_PREFIX.length()).trim();

            try {
                Claims claims = jwtService.parseClaims(token);
                Object idClaim = claims.get("userId");
                String roleClaim = claims.get("role", String.class);

                if (idClaim instanceof Number id && roleClaim != null) {
                    AuthenticatedUser principal = new AuthenticatedUser(
                            id.longValue(), claims.getSubject(), Role.valueOf(roleClaim));

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    principal,
                                    null,
                                    List.of(new SimpleGrantedAuthority("ROLE_" + principal.getRole().name())));
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    request.setAttribute(AUTH_ERROR_ATTRIBUTE, "Invalid token.");
                }
            } catch (ExpiredJwtException ex) {
                request.setAttribute(AUTH_ERROR_ATTRIBUTE, "Token has expired. Please log in again.");
            } catch (JwtException | IllegalArgumentException ex) {
                request.setAttribute(AUTH_ERROR_ATTRIBUTE, "Invalid token.");
            }
        }

        filterChain.doFilter(request, response);
    }
}