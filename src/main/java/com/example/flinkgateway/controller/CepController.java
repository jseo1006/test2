package com.example.flinkgateway.controller;

import com.example.flinkgateway.dto.CepRuleUpdateRequest;
import com.example.flinkgateway.model.WorkflowDeployment;
import com.example.flinkgateway.service.WorkflowService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cep")
public class CepController {

    private final WorkflowService workflowService;

    public CepController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @PatchMapping("/rules")
    public ResponseEntity<WorkflowDeployment> updateRule(@RequestBody @Valid CepRuleUpdateRequest request) {
        return ResponseEntity.ok(workflowService.updateCepRule(request.jobId(), request.ruleJson()));
    }
}
