package com.kefiya.home.configs.security.access;

import io.github.bucket4j.Bucket;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    public FilterRegistrationBean<RateFilter> rateLimitingFilterRegistration(Bucket bucket) {
        FilterRegistrationBean<RateFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RateFilter(bucket));
        registrationBean.addUrlPatterns("/*"); // Apply to all URLs
        return registrationBean;
    }
}
