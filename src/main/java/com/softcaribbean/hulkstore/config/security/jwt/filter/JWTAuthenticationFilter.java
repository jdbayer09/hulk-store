package com.softcaribbean.hulkstore.config.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softcaribbean.hulkstore.api.models.domain.User;
import com.softcaribbean.hulkstore.api.models.request.AuthenticationRequest;
import com.softcaribbean.hulkstore.api.models.response.AuthenticationResponse;
import com.softcaribbean.hulkstore.api.models.response.ErrorResponse;
import com.softcaribbean.hulkstore.config.security.jwt.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authManager;
    private final JWTService jwtService;


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
        this.authManager = authenticationManager;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
        this.jwtService = jwtService;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AuthenticationRequest authenticationRequest;
        Authentication authResp;
        try {
            authenticationRequest = new ObjectMapper().readValue(request.getInputStream(), AuthenticationRequest.class);
        } catch (IOException e) {
            throw new UsernameNotFoundException("Problemas al leer los datos, verifica que todos sean correctos: ['usuario', 'contrasena']");
        }
        try {
            authResp = authManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    authenticationRequest.getEmail(),
                                    authenticationRequest.getPassword()
                            )
                    );
        } catch (AuthenticationException ae) {
            if (ae.getMessage().equals("User is disabled"))
                throw new UsernameNotFoundException("El usuario esta Deshabilitado!", ae);
            else if (ae.getMessage().equals("Bad credentials"))
                throw new UsernameNotFoundException("Usuario o contraseña incorrectos!", ae);
            else
                throw new UsernameNotFoundException("Error al ingresar tus credenciales!", ae);
        }
        return authResp;
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        User userData = ((User) authResult.getPrincipal());
        String token = jwtService.createToken(authResult);
        response.addHeader(JWTService.HEADER_STRING, JWTService.TOKEN_PREFIX + token);

        AuthenticationResponse body = new AuthenticationResponse();

        body.setToken(token);
        body.setEmail(userData.getEmail());
        body.setId(userData.getId());
        body.setFullName(userData.getName() + " " + userData.getLastName());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json");
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.getWriter().write(new ObjectMapper().writeValueAsString(new ErrorResponse("¡Credenciales no validas!", 401)));
        response.setStatus(401);
        response.setContentType("application/json");
    }
}
