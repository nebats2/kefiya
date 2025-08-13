package com.kefiya.home.common;

import lombok.Data;

@Data
public class BaseRequest {
    String api;
    String appName;
    String appVersion;
    String language;
    String apiRequestId;
}
