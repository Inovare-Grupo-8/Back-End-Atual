package org.com.imaapi.infrastructure.adapter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.security.Keys;
import org.com.imaapi.domain.gateway.TokenProvider;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

public class GerenciadorTokenJwtAdapter implements TokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.validity}")
    private long jwtTokenValidity;

    public String extrairUsernameFromToken(String token) {
        return getClaimForToken(token, Claims::getSubject);
    }

    public Date extrairExpirationDateFromToken(String token) {
        return getClaimForToken(token, Claims::getExpiration);
    }

    public String gerarToken(String username, List<String> authorities) {

        final String roles = String.join(",", authorities);

        return Jwts.builder()
                .subject(username)
                .claim("authorities", roles)
                .signWith(parseSecret())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtTokenValidity * 1000)).compact();
    }

    public <T> T getClaimForToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public boolean validarToken(String usuarioAutenticado, String token) {
        String username = extrairUsernameFromToken(token);
        return (username.equals(usuarioAutenticado) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extrairExpirationDateFromToken(token);
        return expiration.before(new Date(System.currentTimeMillis()));
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(parseSecret())
                .build()
                .parseSignedClaims(token).getBody();
    }

    private SecretKey parseSecret() {
        return Keys.hmacShaKeyFor(this.secret.getBytes(StandardCharsets.UTF_8));
    }

}
