package com.eco.beminichat.config;

import com.eco.beminichat.exceptions.CustomException;
import com.eco.beminichat.response.base.ResponseObject;
import com.eco.beminichat.services.AccountService;
import com.eco.beminichat.services.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
//@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AccountService accountService;

    @Override
    protected void doFilterInternal
            (
                    HttpServletRequest request,
                    HttpServletResponse response,
                    FilterChain filterChain
            ) throws ServletException, IOException {

//        log.info("Request to: {}", request.getRequestURL().toString());

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
//            log.warn("Request not contains token : %s".formatted(request.getRequestURL().toString()));
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        String username;

        try {
            username = jwtService.extractUsername(token);
        } catch (ExpiredJwtException e) {
            handlerException(response, "Token is expired", HttpStatus.UNAUTHORIZED);
            return;
        } catch (Exception e) {
            handlerException(response, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = accountService.loadUserByUsername(username);

            if (jwtService.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                auth.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(auth);

               /* log.info("Authenticated user: %s from endpoint: %s".formatted(
                        username,
                        request.getRequestURL().toString()
                ));*/
            }
        }

        filterChain.doFilter(request, response);

    }

    private void handlerException(HttpServletResponse response, String message, HttpStatus status) {
        try {
            ResponseObject<Object> responseObject = ResponseObject
                    .builder()
                    .message(message)
                    .status(status.getReasonPhrase())
                    .build();

            String responseText = new ObjectMapper().writeValueAsString(responseObject);

            response.setStatus(status.value());
            response.setContentType("application/json");
            response.getWriter().write(responseText);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
