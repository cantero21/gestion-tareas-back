package com.cantero.gestiontareas.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // Clave secreta para firmar el token (en producción va en application.properties)
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // El token dura 24 horas
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    // Genera un token para el usuario
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    // Extrae el username del token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Valida si el token es correcto y no expiró
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Verifica si el token expiró
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Extrae la información del token
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}