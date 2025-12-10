package org.com.imaapi.infrastructure.config.autenticacao;


import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.com.imaapi.infrastructure.adapter.AutenticacaoServiceAdapter;
import org.com.imaapi.infrastructure.adapter.GerenciadorTokenJwtAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AutenticacaoFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutenticacaoFilter.class);

    private final AutenticacaoServiceAdapter autenticacaoServiceAdapter;

    private final GerenciadorTokenJwtAdapter jwtTokenManager;

    public AutenticacaoFilter(AutenticacaoServiceAdapter autenticacaoServiceAdapter, GerenciadorTokenJwtAdapter tokenManager) {
        this.autenticacaoServiceAdapter = autenticacaoServiceAdapter;
        this.jwtTokenManager = tokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = header.substring(7).trim();
        if (jwtToken.isEmpty()) {
            LOGGER.warn("[FALHA AUTENTICACAO] - Token vazio recebido no header Authorization.");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String username = jwtTokenManager.extrairUsernameFromToken(jwtToken);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                addUsernameInContext(request, username, jwtToken);
            }

        } catch (ExpiredJwtException e) {
            LOGGER.info("[TOKEN EXPIRADO] Usuário: {} - {}", e.getClaims().getSubject(), e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;

        } catch (Exception e) {
            LOGGER.error("[ERRO TOKEN] Token inválido ou malformado: {}", e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void addUsernameInContext(HttpServletRequest request, String username, String jwtToken) {
        UserDetails userDetails = autenticacaoServiceAdapter.loadUserByUsername(username);

        if (jwtTokenManager.validarToken(userDetails.getUsername(), jwtToken)) {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }

}
