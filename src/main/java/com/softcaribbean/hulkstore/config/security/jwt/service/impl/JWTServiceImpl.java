package com.softcaribbean.hulkstore.config.security.jwt.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softcaribbean.hulkstore.api.models.domain.User;
import com.softcaribbean.hulkstore.config.security.SimpleGrantedAuthorityMixin;
import com.softcaribbean.hulkstore.config.security.jwt.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {
    @Value("${hulk-store.jwt.secret-key}")
    private String secretKey;
    @Value("${hulk-store.jwt.application-name}")
    private String applicationName;
    @Value("${hulk-store.jwt.expiration-token-hours}")
    private long expirationTokenHours;

    @Override
    public String getUserApp(String token) {
        return getClaimsToken(token, Claims::getSubject);
    }
    @Override
    public String createToken(Authentication auth) throws IOException {
        Claims extraClaims = Jwts.claims();
        extraClaims.put("authorities", new ObjectMapper().writeValueAsString(auth.getAuthorities()));

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(((User) auth.getPrincipal()).getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * expirationTokenHours)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .setId(applicationName)
                .compact();
    }
    private boolean isTokenExpired(String token) {
        return extraerExpiracion(token).before(new Date());
    }
    private Date extraerExpiracion(String token) {
        return getClaimsToken(token, Claims::getExpiration);
    }
    @Override
    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
    @Override
    public Collection<SimpleGrantedAuthority> getRoles(String token) throws IOException {
        Object roles = getClaimsToken(token, claims -> claims.get("authorities"));
        return Arrays.asList(new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
                .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
    }
    private <T> T getClaimsToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(resolve(token))
                .getBody();
    }
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private String resolve(String token) {
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            return token.replace(TOKEN_PREFIX, "");
        }
        return null;
    }
}
