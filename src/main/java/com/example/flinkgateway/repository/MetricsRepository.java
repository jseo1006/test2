package com.example.flinkgateway.repository;

import com.example.flinkgateway.model.JobMetrics;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MetricsRepository {
    private final Map<String, JobMetrics> metrics = new ConcurrentHashMap<>();

    public void save(JobMetrics jobMetrics) {
        metrics.put(jobMetrics.jobId(), jobMetrics);
    }

    public Optional<JobMetrics> findByJobId(String jobId) {
        return Optional.ofNullable(metrics.get(jobId));
    }
}
