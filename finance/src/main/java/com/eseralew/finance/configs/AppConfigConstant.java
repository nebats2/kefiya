package com.kefiya.home.configs;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app")
@Data
public class AppConfigConstant {
    String apikey;
    String clientId;
    String name;
    String secret;
    Integer retryTimeoutSecond;
    String sessionExpirationTime;
    String jwtTokenExpirationTime;
    String hashKey;

    String host;
    String userUrl;
    String mapUrl;
    String notificationUrl;

    String rootPhotoPath;

    Integer rate_token;
    Integer rate_capacity;

    List<String> permittedApis;

    float imageCompressionQuality;
}
