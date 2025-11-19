package com.example.flinkgateway.config;

import com.example.flinkgateway.service.ApiGatewayService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiGatewayFilter extends OncePerRequestFilter {

    private final ApiGatewayService gatewayService;

    public ApiGatewayFilter(ApiGatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/api/sessions");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!gatewayService.authenticate(request)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Invalid or expired session");
            return;
        }
        if (!gatewayService.authorize(request)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Insufficient permissions");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
