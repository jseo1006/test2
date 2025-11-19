package com.example.flinkgateway.model;

import java.time.Instant;
import java.util.Optional;

public class WorkflowDeployment {
    private final String jobId;
    private final String userId;
    private final String workflowName;
    private final String jarPath;
    private String cepRule;
    private Instant lastUpdated;

    public WorkflowDeployment(String jobId, String userId, String workflowName, String jarPath, String cepRule) {
        this.jobId = jobId;
        this.userId = userId;
        this.workflowName = workflowName;
        this.jarPath = jarPath;
        this.cepRule = cepRule;
        this.lastUpdated = Instant.now();
    }

    public String getJobId() {
        return jobId;
    }

    public String getUserId() {
        return userId;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public String getJarPath() {
        return jarPath;
    }

    public String getCepRule() {
        return cepRule;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void updateCepRule(String newRule) {
        this.cepRule = newRule;
        this.lastUpdated = Instant.now();
    }

    public Optional<String> safeCepRule() {
        return Optional.ofNullable(cepRule);
    }
}
