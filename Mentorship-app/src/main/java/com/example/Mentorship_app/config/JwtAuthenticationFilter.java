package com.example.Mentorship_app.config;

import com.example.Mentorship_app.Services.jwt.ComposedDetailsService;
import com.example.Mentorship_app.Services.jwt.MentorService;
import com.example.Mentorship_app.Utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter {
    private final JwtUtil jwtutil;
    private final ComposedDetailsService composedService;

    public JwtAuthenticationFilter(
            JwtUtil jwtutil,
            ComposedDetailsService composedService

    ) {
        this.jwtutil = jwtutil;
        this.composedService = composedService;

    }
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String jwt = authHeader.substring(7);
        final String userEmail = jwtutil.extractUserName(jwt);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (userEmail != null && authentication == null) {
            UserDetails userDetails = composedService.loadUserByUsername(userEmail);


            if (jwtutil.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);


    }
}
