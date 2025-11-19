package com.example.flinkgateway.service;

import com.example.flinkgateway.model.FlinkJobDescriptor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;

@Service
public class JarDeploymentService {

    public String packageAsJar(FlinkJobDescriptor descriptor) {
        try {
            Path jarDirectory = Path.of("target", "generated-jars");
            Files.createDirectories(jarDirectory);
            Path jarPath = jarDirectory.resolve(descriptor.jobId() + ".jar");
            String metadata = "job=" + descriptor.jobName() + "\n" + descriptor.sourceCode();
            Files.writeString(jarPath, metadata);
            return jarPath.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new IllegalStateException("Jar 파일을 생성할 수 없습니다.", e);
        }
    }

    public Instant deployJar(String jarPath) {
        return Instant.now();
    }
}
