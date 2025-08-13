package com.kefiya.home.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "arifpay")
@Data
public class ArifPaySettings {

    String cancelUrl;
    String expiredUrl;
    String notifyUrl;
    String errorUrl;
    String baseUrl;
    String checkoutUrl;
    String successUrl;

    String apiKey;
}
