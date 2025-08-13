package com.kefiya.home.apis.appsClient.models;

import com.kefiya.home.apis.appsClient.enums.ApiClientNameEnum;
import lombok.Data;

@Data
public class ApiClient {
    int id;
    ApiClientNameEnum name;
    String secret;
    String apikey;
    Boolean enabled;
}
