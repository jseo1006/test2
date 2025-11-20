package com.example.flinkgateway.model;

import java.util.Map;

public record WorkflowNode(String id, String type, Map<String, Object> config) {
}
