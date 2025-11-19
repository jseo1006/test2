package com.example.flinkgateway.service;

import com.example.flinkgateway.dto.DeploymentRequest;
import com.example.flinkgateway.model.FlinkJobDescriptor;
import com.example.flinkgateway.model.WorkflowDeployment;
import com.example.flinkgateway.repository.WorkflowRepository;
import org.springframework.stereotype.Service;

@Service
public class WorkflowService {
    private final WorkflowCompilerService compilerService;
    private final JarDeploymentService jarDeploymentService;
    private final WorkflowRepository workflowRepository;

    public WorkflowService(WorkflowCompilerService compilerService,
                           JarDeploymentService jarDeploymentService,
                           WorkflowRepository workflowRepository) {
        this.compilerService = compilerService;
        this.jarDeploymentService = jarDeploymentService;
        this.workflowRepository = workflowRepository;
    }

    public FlinkJobDescriptor compileWorkflow(String workflowJson) {
        return compilerService.compile(workflowJson);
    }

    public WorkflowDeployment deployWorkflow(DeploymentRequest request) {
        FlinkJobDescriptor descriptor = compileWorkflow(request.workflowJson());
        String jarPath = jarDeploymentService.packageAsJar(descriptor);
        jarDeploymentService.deployJar(jarPath);
        WorkflowDeployment deployment = new WorkflowDeployment(descriptor.jobId(), request.userId(), descriptor.jobName(), jarPath,
                (String) request.deploymentConfig().getOrDefault("cepRule", ""));
        workflowRepository.save(deployment);
        return deployment;
    }

    public WorkflowDeployment updateCepRule(String jobId, String ruleJson) {
        WorkflowDeployment deployment = workflowRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job을 찾을 수 없습니다."));
        deployment.updateCepRule(ruleJson);
        workflowRepository.save(deployment);
        return deployment;
    }
}
