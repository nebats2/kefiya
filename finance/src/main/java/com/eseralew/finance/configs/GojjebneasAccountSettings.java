package com.kefiya.home.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "company")
@Data
public class GojjebneasAccountSettings {
    String mobile;
    String email;

    String cbe;
    String boa;
    String telebirr;
}
