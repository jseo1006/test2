package com.example.flinkgateway.model;

import java.util.List;

public record FlinkJobDescriptor(String jobId, String jobName, List<String> operators, String sourceCode) {
}
