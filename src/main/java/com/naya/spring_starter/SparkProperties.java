package com.naya.spring_starter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spark.context")
@Getter
@Setter
public class SparkProperties {

    private String appName;
    private String master;
}
