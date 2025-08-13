package com.kefiya.home.services.controllers;

import com.kefiya.home.apis.appsClient.enums.ApiClientNameEnum;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

@Service
@RequestScope
@Data
public class ContextService {

    String appClientId;
    Long requestedUserId;
    Long staffId;
    Long profileId;

    ApiClientNameEnum apiClientName;

    public boolean isAdmin(){
        return this.apiClientName.equals(ApiClientNameEnum.ESERALEW_ADMIN_AUTH);
    }
    public boolean isProfile(){
        return this.apiClientName.equals(ApiClientNameEnum.ESERALEW_PROFILE_AUTH);
    }

    public boolean isFinance(){
        return apiClientName == ApiClientNameEnum.ESERALEW_FINANCE;
    }

}
