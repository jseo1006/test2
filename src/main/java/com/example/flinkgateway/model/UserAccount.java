package com.example.flinkgateway.model;

import java.util.Set;

public record UserAccount(String userId, String username, String password, Set<String> roles) {
}
