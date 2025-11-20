package com.example.flinkgateway.controller;

import com.example.flinkgateway.dto.DeploymentRequest;
import com.example.flinkgateway.dto.WorkflowCompileRequest;
import com.example.flinkgateway.model.FlinkJobDescriptor;
import com.example.flinkgateway.model.WorkflowDeployment;
import com.example.flinkgateway.service.WorkflowService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workflows")
public class WorkflowController {

    private final WorkflowService workflowService;

    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @PostMapping("/compile")
    public ResponseEntity<FlinkJobDescriptor> compileWorkflow(@RequestBody @Valid WorkflowCompileRequest request) {
        return ResponseEntity.ok(workflowService.compileWorkflow(request.workflowJson()));
    }

    @PostMapping("/deploy")
    public ResponseEntity<WorkflowDeployment> deployWorkflow(@RequestBody @Valid DeploymentRequest request) {
        return ResponseEntity.ok(workflowService.deployWorkflow(request));
    }
}
