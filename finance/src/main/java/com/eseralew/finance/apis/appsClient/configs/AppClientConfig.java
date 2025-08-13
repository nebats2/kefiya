package com.kefiya.home.apis.appsClient.configs;

import com.kefiya.home.apis.appsClient.entities.repos.AppClientJpaRepo;
import com.kefiya.home.apis.appsClient.models.AppClientListModel;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class AppClientConfig {
    private final AppClientJpaRepo appClientJpaRepo;

    @Bean
    public AppClientListModel appClients(){
        var appClientListModel = new AppClientListModel();
        appClientListModel.setAppClients(appClientJpaRepo.findAllByEnabled(true));
        return appClientListModel;
    }
}
