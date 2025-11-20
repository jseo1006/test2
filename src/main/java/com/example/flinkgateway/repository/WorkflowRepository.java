package com.example.flinkgateway.repository;

import com.example.flinkgateway.model.WorkflowDeployment;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class WorkflowRepository {
    private final Map<String, WorkflowDeployment> deployments = new ConcurrentHashMap<>();

    public void save(WorkflowDeployment deployment) {
        deployments.put(deployment.getJobId(), deployment);
    }

    public Optional<WorkflowDeployment> findById(String jobId) {
        return Optional.ofNullable(deployments.get(jobId));
    }

    public Collection<WorkflowDeployment> findByUserId(String userId) {
        return deployments.values().stream()
                .filter(deployment -> deployment.getUserId().equals(userId))
                .toList();
    }
}
