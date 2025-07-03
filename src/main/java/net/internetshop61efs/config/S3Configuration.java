package net.internetshop61efs.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "s3")
@Data
public class S3Configuration {

    private String accessKey;
    private String secretKey;
    private String endpoint;
    private String region;

}
