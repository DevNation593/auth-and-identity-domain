package com.unisocial.userservice.security;

import com.unisocial.userservice.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey getSigningKey() {
        // Asegurarse de que la clave tenga al menos 64 bytes (512 bits)
        byte[] keyBytes = Base64.getDecoder().decode(generateSecureSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String generateSecureSecret() {
        // Si no hay una clave secreta configurada, generar una segura
        if (secret == null || secret.trim().isEmpty()) {
            SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            return Base64.getEncoder().encodeToString(key.getEncoded());
        }
        // Si hay una clave configurada, asegurarse de que tenga el tamaño correcto
        if (secret.length() < 64) {
            StringBuilder paddedSecret = new StringBuilder(secret);
            while (paddedSecret.length() < 64) {
                paddedSecret.append("0");
            }
            return Base64.getEncoder().encodeToString(paddedSecret.toString().getBytes(StandardCharsets.UTF_8));
        }
        return secret;
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
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
}