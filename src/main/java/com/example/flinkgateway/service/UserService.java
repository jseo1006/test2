package com.example.flinkgateway.service;

import com.example.flinkgateway.model.UserAccount;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    private final Map<String, UserAccount> accounts = new ConcurrentHashMap<>();

    @PostConstruct
    void seedUsers() {
        accounts.put("admin", new UserAccount("1", "admin", "admin", Set.of("ADMIN", "USER")));
        accounts.put("analyst", new UserAccount("2", "analyst", "analyst", Set.of("USER")));
    }

    public Optional<UserAccount> findByUsername(String username) {
        return Optional.ofNullable(accounts.get(username));
    }
}
