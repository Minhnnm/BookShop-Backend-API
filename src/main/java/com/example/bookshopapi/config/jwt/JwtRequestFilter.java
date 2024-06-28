package com.example.bookshopapi.config.jwt;

import com.example.bookshopapi.exception.UnAuthorizedException;
import com.example.bookshopapi.exception.handler.GlobalExceptionError;
import com.example.bookshopapi.service.UserService;
import com.example.bookshopapi.service.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "*");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        // Xác minh JWT
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String token = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            token = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(token);
            } catch (IllegalArgumentException e) {
                log.error("IllegalArgumentException: " + e.getMessage());
                sendErrorResponse(response, "Invalid JWT", "JWT_02");
                return;
            } catch (ExpiredJwtException e) {
                log.error("ExpiredJwtException: " + e.getMessage());
                sendErrorResponse(response, "JWT Token has expired", "JWT_01");
                return;
            } catch (MalformedJwtException e) {
                log.error("MalformedJwtException: " + e.getMessage());
                sendErrorResponse(response, "Unable to get JWT Token", "JWT_03");
                return;
            } catch (SignatureException e) {
                log.error("SignatureException: " + e.getMessage());
                sendErrorResponse(response, "Invalid JWT Signature", "JWT_04");
                return;
            } catch (UnsupportedJwtException e) {
                log.error("UnsupportedJwtException: " + e.getMessage());
                sendErrorResponse(response, "Unsupported JWT", "JWT_05");
                return;
            }
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);
            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, String message, String errorCode) throws IOException {
        GlobalExceptionError error = GlobalExceptionError.builder().
                timestamp(LocalDateTime.now()).
                status(HttpStatus.UNAUTHORIZED).
                errorCode(errorCode).message(message).
                field("Authorization").build();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        PrintWriter out = response.getWriter();
        mapper.writeValue(out, error);
        out.flush();
    }
}
