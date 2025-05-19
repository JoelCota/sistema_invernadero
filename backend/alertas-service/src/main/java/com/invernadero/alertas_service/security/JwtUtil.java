package com.invernadero.alertas_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    public Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            // Limpia espacios y posibles prefijos
            String tokenLimpio = token.replace("Bearer ", "").trim();

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(tokenLimpio)
                    .getBody();
            return claims.getSubject();
        } catch (JwtException e) {
            System.out.println("Error parsing token: " + e.getMessage());
            return null;
        }
    }
    public Long getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            System.out.println(claims);
            return claims.get("userId", Long.class); // extrae el claim userId
        } catch (JwtException e) {
            return null;
        }
    }

    public String generarTokenDeSistema() {
        return Jwts.builder()
                .setSubject("sistema-invernadero")
                .claim("userId", 0)
                .signWith(getSigningKey())
                .compact();
    }

}
