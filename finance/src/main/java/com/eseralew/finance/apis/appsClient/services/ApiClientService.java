package com.kefiya.home.apis.appsClient.services;

import com.kefiya.home.apis.appsClient.enums.ApiClientNameEnum;
import com.kefiya.home.apis.appsClient.models.AppClientListModel;
import com.kefiya.home.common.BaseException;
import com.kefiya.home.common.ErrorMessage;
import com.kefiya.home.apis.appsClient.models.ApiClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApiClientService {

    private final AppClientListModel appClientListModel;

    public ApiClient getClient(int clientId) throws BaseException {
        var clients = appClientListModel.getAppClients()
                .stream()
                .filter(e->e.getEnabled() && e.getClientId().equals(clientId))
                .findAny();
        if(clients.isEmpty()) {
          throw new BaseException(ErrorMessage.app_client_is_not_found);

        }
        var client = clients.get();

        var apiClient = new ApiClient();
        apiClient.setId(client.getClientId());
        apiClient.setSecret(client.getSecret());
        apiClient.setName(ApiClientNameEnum.getName(client.getApiClientName()));
        apiClient.setApikey(client.getApiKey());
        apiClient.setEnabled(client.getEnabled());
        return apiClient;

    }


}
