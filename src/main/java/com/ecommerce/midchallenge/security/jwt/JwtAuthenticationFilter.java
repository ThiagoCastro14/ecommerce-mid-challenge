package com.ecommerce.midchallenge.security.jwt;

import com.ecommerce.midchallenge.service.CustomUserDetailsService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        logger.info("=== Processando requisicao: {} ===", requestURI);

        String authHeader = request.getHeader("Authorization");
        logger.info("Authorization header: {}", authHeader != null ? "presente" : "ausente");
        
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            logger.info("Token extraido (primeiros 20 chars): {}", token.substring(0, Math.min(20, token.length())));
            
            try {
                username = jwtService.extractUsername(token);
                logger.info("Username extraido do token: {}", username);
            } catch (Exception e) {
                logger.error("ERRO ao extrair username do token: {}", e.getMessage());
                filterChain.doFilter(request, response);
                return;
            }
        } else {
            logger.warn("Token Bearer nao encontrado no header Authorization");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                var userDetails = userDetailsService.loadUserByUsername(username);
                logger.info("UserDetails carregado para usuario: {}", userDetails.getUsername());

                if (jwtService.isTokenValid(token)) {
                    logger.info("Token VALIDO! Autenticando usuario...");
                    var authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.info("Usuario autenticado com sucesso! Authorities: {}", userDetails.getAuthorities());
                } else {
                    logger.error("Token INVALIDO!");
                }
            } catch (Exception e) {
                logger.error("ERRO na autenticacao do usuario: {}", e.getMessage(), e);
            }
        } else if (username == null) {
            logger.warn("Username null - nao foi possivel extrair do token");
        } else {
            logger.info("Usuario ja autenticado no SecurityContext");
        }
        
        logger.info("=== Fim do processamento do filtro JWT ===");
        filterChain.doFilter(request, response);
    }
}