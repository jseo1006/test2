package com.example.flinkgateway.model;

import java.time.Instant;
import java.util.Set;

public record UserSession(String sessionId, String userId, Set<String> roles, Instant createdAt) {
    public boolean isExpired(long ttlSeconds) {
        return createdAt.plusSeconds(ttlSeconds).isBefore(Instant.now());
    }
}
