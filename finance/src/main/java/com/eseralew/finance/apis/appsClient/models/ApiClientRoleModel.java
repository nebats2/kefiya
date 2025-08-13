package com.kefiya.home.apis.appsClient.models;

import com.kefiya.home.apis.appsClient.enums.ApiClientNameEnum;
import com.kefiya.home.configs.security.apiClients.UserApiRolesEnum;

public record ApiClientRoleModel (
    ApiClientNameEnum apiClientName,
    UserApiRolesEnum role)
{
}
