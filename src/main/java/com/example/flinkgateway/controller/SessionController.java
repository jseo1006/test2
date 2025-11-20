package com.example.flinkgateway.controller;

import com.example.flinkgateway.dto.SessionRequest;
import com.example.flinkgateway.model.UserSession;
import com.example.flinkgateway.service.SessionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public ResponseEntity<?> createSession(@RequestBody @Valid SessionRequest request) {
        return sessionService.createSession(request)
                .<ResponseEntity<?>>map(session -> ResponseEntity.ok().body(session))
                .orElseGet(() -> ResponseEntity.status(401).body("인증 실패"));
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<UserSession> getSession(@PathVariable String sessionId) {
        return sessionService.findById(sessionId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> deleteSession(@PathVariable String sessionId) {
        sessionService.delete(sessionId);
        return ResponseEntity.noContent().build();
    }
}
