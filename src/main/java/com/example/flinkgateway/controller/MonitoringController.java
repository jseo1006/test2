package com.example.flinkgateway.controller;

import com.example.flinkgateway.dto.MetricsUpdateRequest;
import com.example.flinkgateway.model.JobMetrics;
import com.example.flinkgateway.model.WorkflowDeployment;
import com.example.flinkgateway.service.MonitoringService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class MonitoringController {

    private final MonitoringService monitoringService;

    public MonitoringController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping("/users/{userId}/processes")
    public ResponseEntity<Collection<WorkflowDeployment>> getDeployments(@PathVariable String userId) {
        return ResponseEntity.ok(monitoringService.getDeploymentsForUser(userId));
    }

    @GetMapping("/processes/{jobId}/metrics")
    public ResponseEntity<JobMetrics> getMetrics(@PathVariable String jobId) {
        return ResponseEntity.ok(monitoringService.getMetrics(jobId));
    }

    @PostMapping("/processes/{jobId}/metrics")
    public ResponseEntity<JobMetrics> updateMetrics(@PathVariable String jobId,
                                                    @RequestBody @Valid MetricsUpdateRequest request) {
        JobMetrics metrics = new JobMetrics(jobId, request.throughputPerSecond(), request.endToEndLatencyMs());
        monitoringService.updateMetrics(metrics);
        return ResponseEntity.ok(metrics);
    }
}
