package com.example.flinkgateway.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record DeploymentRequest(
        @NotBlank String userId,
        @NotBlank String workflowJson,
        @NotNull Map<String, Object> deploymentConfig
) {}
