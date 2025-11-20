package com.example.flinkgateway.model;

public record JobMetrics(String jobId, double throughputPerSecond, double endToEndLatencyMs) {
}
