package io.github.sskorol.model;

import io.github.sskorol.data.Source;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@Source(path = "docker-compose.yml")
public class DockerConfiguration {

    private String version;
    private Map<String, ServiceConfiguration> services;
}
