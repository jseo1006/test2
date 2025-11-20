package com.example.flinkgateway.service;

import com.example.flinkgateway.model.FlinkJobDescriptor;
import com.example.flinkgateway.model.WorkflowConnection;
import com.example.flinkgateway.model.WorkflowDefinition;
import com.example.flinkgateway.model.WorkflowNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WorkflowCompilerService {
    private final ObjectMapper mapper = new ObjectMapper();

    public FlinkJobDescriptor compile(String workflowJson) {
        try {
            JsonNode root = mapper.readTree(workflowJson);
            WorkflowDefinition definition = mapper.treeToValue(root, WorkflowDefinition.class);
            List<String> operators = buildOperators(definition);
            String sourceCode = generateSourceCode(definition, operators);
            return new FlinkJobDescriptor(UUID.randomUUID().toString(), definition.name(), operators, sourceCode);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Workflow JSON을 파싱할 수 없습니다.", e);
        }
    }

    private List<String> buildOperators(WorkflowDefinition definition) {
        List<String> operators = new ArrayList<>();
        for (WorkflowNode node : definition.nodes()) {
            operators.add("operator:" + node.type());
        }
        for (WorkflowConnection connection : definition.connections()) {
            operators.add("connection:" + connection.source() + "->" + connection.target());
        }
        return operators;
    }

    private String generateSourceCode(WorkflowDefinition definition, List<String> operators) {
        StringBuilder builder = new StringBuilder();
        builder.append("// Auto-generated Flink job for ").append(definition.name()).append("\n");
        builder.append("public class GeneratedJob {\n");
        builder.append("    public void build(org.apache.flink.streaming.api.environment.StreamExecutionEnvironment env) {\n");
        for (String operator : operators) {
            builder.append("        // ").append(operator).append("\n");
        }
        builder.append("    }\n");
        builder.append("}\n");
        return builder.toString();
    }
}
