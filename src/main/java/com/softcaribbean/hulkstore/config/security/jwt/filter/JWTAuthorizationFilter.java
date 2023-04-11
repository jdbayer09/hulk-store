package com.softcaribbean.hulkstore.config.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softcaribbean.hulkstore.api.models.response.ErrorResponse;
import com.softcaribbean.hulkstore.config.security.jwt.service.JWTService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private final JWTService jwtService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            String header = request.getHeader("Authorization");

            if (!requiresAuthentication(header)) {
                chain.doFilter(request, response);
                return;
            }
            if (jwtService.validateToken(header)) {
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(
                                new UsernamePasswordAuthenticationToken(
                                        jwtService.getUserApp(header),
                                        null,
                                        jwtService.getRoles(header)
                                )
                        );
                chain.doFilter(request, response);
            }
        }catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException |  IllegalArgumentException ex ) {
            log.error(ex.getMessage(), ex);
            if (ex instanceof ExpiredJwtException) {
                response.getWriter().write(new ObjectMapper().writeValueAsString(new ErrorResponse("El token ha expirado", 403)));
                response.setStatus(403);
            } else {
                response.getWriter().write(new ObjectMapper().writeValueAsString(new ErrorResponse(ex.getMessage(), 401)));
                response.setStatus(401);
            }
            response.setContentType("application/json");
        }

    }
    private boolean requiresAuthentication(String header) {
        return header != null && header.startsWith(JWTService.TOKEN_PREFIX);
    }
}
