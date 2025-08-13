package com.kefiya.home.configs.security.access;

import com.kefiya.home.configs.AppConfigConstant;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@AllArgsConstructor
public class RateConfiguration {
    private final AppConfigConstant appConfigConstant;
    @Bean
    public Bucket bucket() {
        Refill refill = Refill.greedy(appConfigConstant.getRate_token(), Duration.ofMinutes(1)); // 60 requests per minute
        Bandwidth limit = Bandwidth.classic(appConfigConstant.getRate_capacity(), refill);
        return Bucket4j.builder()
                .addLimit(limit)
                .build();
    }
}
