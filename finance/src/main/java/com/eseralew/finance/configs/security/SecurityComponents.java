package com.kefiya.home.configs.security;

import com.kefiya.home.common.ApiRequestHeaders;
import com.kefiya.home.configs.security.apiClients.ApiAuthProvider;
import com.kefiya.home.apis.appsClient.services.ApiClientService;
import com.kefiya.home.configs.security.apiClients.ApiKeyAuthFilter;
import com.kefiya.home.services.controllers.ContextService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityComponents  {

    final ApiAuthProvider apiAuthProvider;
    final ApiClientService apiClientService;
    final ContextService contextService;
    final ApiRequestHeaders apiRequestHeaders;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        ApiKeyAuthFilter filter = new ApiKeyAuthFilter(apiClientService, contextService,apiRequestHeaders);
        filter.setAuthenticationManager(authenticationManager());

         http .authorizeHttpRequests(requests ->
                 requests.requestMatchers("/swagger-ui/**","/api-docs/**","/swagger-resources/*","/v3/api-docs/**").permitAll()

            ) .authorizeHttpRequests(requests ->
                        requests.anyRequest().authenticated()

                )

                . csrf(AbstractHttpConfigurer::disable)
                .addFilter(filter);
                //.httpBasic(Customizer.withDefaults());
                return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(apiKeyAuthProvider()));
    }

    @Bean
    public ApiAuthProvider apiKeyAuthProvider() {
        return apiAuthProvider;
    }

}
