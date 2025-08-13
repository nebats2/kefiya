package com.kefiya.home.apiRestService;

import com.kefiya.home.common.ApiRequestHeaders;
import com.kefiya.home.configs.AppConfigConstant;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Component
public class RestTemplateConfig {
    final int timeoutSeconds = 20;
    final AppConfigConstant appConfigConstant;

    public RestTemplateConfig(AppConfigConstant appConfigConstant) {
        this.appConfigConstant = appConfigConstant;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(30)) // Connection timeout
                .setReadTimeout(Duration.ofSeconds(30))    // Read timeout
                .defaultHeader(ApiRequestHeaders.X_API_KEY, appConfigConstant.getApikey()) // Example default header
                .defaultHeader(ApiRequestHeaders.X_API_CLIENT_ID, appConfigConstant.getClientId())
                .build();
    }

}

