package com.example.flinkgateway.service;

import com.example.flinkgateway.model.UserSession;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ApiGatewayService {
    private final SessionService sessionService;
    private final Map<String, Set<String>> routePolicies = new ConcurrentHashMap<>();

    public ApiGatewayService(SessionService sessionService) {
        this.sessionService = sessionService;
        routePolicies.put("/api/workflows", Set.of("USER"));
        routePolicies.put("/api/processes", Set.of("USER"));
        routePolicies.put("/api/cep", Set.of("ADMIN"));
    }

    public boolean authenticate(HttpServletRequest request) {
        var sessionId = request.getHeader("X-SESSION-ID");
        if (sessionId == null) {
            return false;
        }
        return sessionService.findById(sessionId).isPresent();
    }

    public boolean authorize(HttpServletRequest request) {
        var sessionId = request.getHeader("X-SESSION-ID");
        if (sessionId == null) {
            return false;
        }
        UserSession session = sessionService.findById(sessionId).orElse(null);
        if (session == null) {
            return false;
        }
        return routePolicies.entrySet().stream()
                .filter(entry -> request.getRequestURI().startsWith(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .map(requiredRoles -> session.roles().stream().anyMatch(requiredRoles::contains))
                .orElse(true);
    }
}
