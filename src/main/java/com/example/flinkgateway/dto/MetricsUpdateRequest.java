package com.example.flinkgateway.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record MetricsUpdateRequest(
        @NotBlank String jobId,
        @Positive double throughputPerSecond,
        @Positive double endToEndLatencyMs
) {}
