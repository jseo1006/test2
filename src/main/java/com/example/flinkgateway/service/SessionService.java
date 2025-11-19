package com.example.flinkgateway.service;

import com.example.flinkgateway.dto.SessionRequest;
import com.example.flinkgateway.model.UserAccount;
import com.example.flinkgateway.model.UserSession;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {
    private static final long TTL_SECONDS = 3600;
    private final Map<String, UserSession> sessions = new ConcurrentHashMap<>();
    private final UserService userService;

    public SessionService(UserService userService) {
        this.userService = userService;
    }

    public Optional<UserSession> createSession(SessionRequest request) {
        return userService.findByUsername(request.username())
                .filter(account -> account.password().equals(request.password()))
                .map(this::createSessionForAccount);
    }

    private UserSession createSessionForAccount(UserAccount account) {
        var session = new UserSession(UUID.randomUUID().toString(), account.userId(), account.roles(), Instant.now());
        sessions.put(session.sessionId(), session);
        return session;
    }

    public Optional<UserSession> findById(String sessionId) {
        var session = sessions.get(sessionId);
        if (session == null) {
            return Optional.empty();
        }
        if (session.isExpired(TTL_SECONDS)) {
            sessions.remove(sessionId);
            return Optional.empty();
        }
        return Optional.of(session);
    }

    public void delete(String sessionId) {
        sessions.remove(sessionId);
    }
}
