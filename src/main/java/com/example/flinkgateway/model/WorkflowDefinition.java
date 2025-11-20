package com.example.flinkgateway.model;

import java.util.List;

public record WorkflowDefinition(String name, List<WorkflowNode> nodes, List<WorkflowConnection> connections) {
}
