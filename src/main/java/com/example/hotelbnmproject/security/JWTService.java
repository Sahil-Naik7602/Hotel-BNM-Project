package com.example.hotelbnmproject.security;

import com.example.hotelbnmproject.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JWTService {

    @Value("${jwt.secretkey}")
    private String jwtSecretKey;

    private SecretKey generateSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }
    public String generateAccessToken(User userEntity){
        return Jwts.builder()
                .subject(userEntity.getId().toString())
                .claim("email",userEntity.getEmail())
                .claim("roles", userEntity.getRoles().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*10))
                .signWith(generateSecretKey())
                .compact();
    }

    public String generateRefreshToken(User userEntity){
        return Jwts.builder()
                .subject(userEntity.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*50))
                .signWith(generateSecretKey())
                .compact();
    }

    public Long getUserIdFromToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(generateSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        System.out.println(claims.getSubject());
        return Long.valueOf(claims.getSubject());
    }
}
