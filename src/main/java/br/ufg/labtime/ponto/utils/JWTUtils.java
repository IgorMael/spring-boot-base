package br.ufg.labtime.ponto.utils;


import br.ufg.labtime.ponto.dto.security.AuthenticationDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JWTUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration_time}")
    private Long expiration;

    public String generateToken(AuthenticationDTO authenticationDTO) {
        String user = GsonUtils.stringify(authenticationDTO);
        return Jwts.builder()
                .setSubject(user)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }

    public String generateSystemToken(String idSystem) {
        return Jwts.builder()
                .setSubject(idSystem)
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }

    public boolean validSystemToken(String token) {
        Claims claims = getClaims(token);

        if (claims != null) {
            return claims.getSubject() != null;
        }
        return false;
    }

    public boolean validToken(String token) {
        Claims claims = getClaims(token);

        if (claims != null) {
            String user = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());

            return user != null && expirationDate != null && now.before(expirationDate);
        }
        return false;
    }

    public Long getSystem(String token) {
        Claims claims = getClaims(token);

        if (claims != null) {
            return Long.parseLong(claims.getSubject());
        }
        return null;
    }

    public AuthenticationDTO getUser(String token) {
        Claims claims = getClaims(token);

        if (claims != null) {
            return (AuthenticationDTO) GsonUtils.fromJson(claims.getSubject(), AuthenticationDTO.class);
        }
        return null;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }
}
