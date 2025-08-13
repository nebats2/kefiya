package com.kefiya.home.apis.appsClient.enums;

import com.kefiya.home.common.BaseException;
import com.kefiya.home.common.ErrorMessage;

public enum ApiClientNameEnum {
    ESERALEW_ADMIN_AUTH,
    ESERALEW_PROFILE_AUTH,
    ESERALEW_USER,
    ESERALEW_PROFILE,
    ESERALEW_SUBSCRIPTION,
    ESERALEW_MAP,
    ESERALEW_NOTIFICATION,
    ESERALEW_FINANCE,
    ESERALEW_REPORT;

    public static ApiClientNameEnum getName(String name) throws BaseException {
        var names = ApiClientNameEnum.values();
        for(var clientName :names){
            if(clientName.name().equals(name )){
                return clientName;
            }
        }
        throw new BaseException(ErrorMessage.app_client_is_not_found);
    }
}
