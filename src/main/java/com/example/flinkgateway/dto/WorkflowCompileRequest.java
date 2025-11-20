package com.example.flinkgateway.dto;

import jakarta.validation.constraints.NotBlank;

public record WorkflowCompileRequest(
        @NotBlank String userId,
        @NotBlank String workflowJson
) {}
