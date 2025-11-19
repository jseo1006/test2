package com.example.flinkgateway.service;

import com.example.flinkgateway.model.JobMetrics;
import com.example.flinkgateway.model.WorkflowDeployment;
import com.example.flinkgateway.repository.MetricsRepository;
import com.example.flinkgateway.repository.WorkflowRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MonitoringService {
    private final WorkflowRepository workflowRepository;
    private final MetricsRepository metricsRepository;

    public MonitoringService(WorkflowRepository workflowRepository, MetricsRepository metricsRepository) {
        this.workflowRepository = workflowRepository;
        this.metricsRepository = metricsRepository;
    }

    public Collection<WorkflowDeployment> getDeploymentsForUser(String userId) {
        return workflowRepository.findByUserId(userId);
    }

    public JobMetrics getMetrics(String jobId) {
        return metricsRepository.findByJobId(jobId)
                .orElse(new JobMetrics(jobId, 0d, 0d));
    }

    public void updateMetrics(JobMetrics jobMetrics) {
        metricsRepository.save(jobMetrics);
    }
}
