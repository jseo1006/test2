package com.example.flinkgateway.dto;

import jakarta.validation.constraints.NotBlank;

public record CepRuleUpdateRequest(
        @NotBlank String jobId,
        @NotBlank String ruleJson
) {}
