package com.kefiya.home.common;

import com.kefiya.home.configs.AppConfigConstant;
import com.kefiya.home.services.controllers.ContextService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Configuration
public class ApiRequestHeaders {
    public static final String X_API_KEY = "X-API-KEY";
    public static final String X_API_CLIENT_ID = "X-API-CLIENT-ID";
    public static final String X_USER_ID = "X-USER-ID";
    public static final String X_STAFF_ID = "X-STAFF-ID";
    public static final String X_REQUEST_ID = "X-REQUEST-ID";
    public static final String X_PROFILE_ID = "X-PROFILE-ID";

    private final AppConfigConstant appConfigConstant;
    private final ContextService contextService;

    public HttpHeaders  getHeaders(){
        var requestUserId = contextService.getRequestedUserId() != null ? contextService.getRequestedUserId() : -1;
        var staffId = contextService.getStaffId() != null ? contextService.getStaffId() : -1;
        var profileId = contextService.getProfileId() != null ? contextService.getProfileId() : -1;
        System.out.println("Profile id = "+profileId);

        HttpHeaders headers = new HttpHeaders();
        headers.add(ApiRequestHeaders.X_USER_ID, String.valueOf(requestUserId));
        headers.add(ApiRequestHeaders.X_API_CLIENT_ID, appConfigConstant.getClientId());
        headers.add(ApiRequestHeaders.X_API_KEY, appConfigConstant.getApikey());
        headers.add(ApiRequestHeaders.X_STAFF_ID, String.valueOf(staffId));
        headers.add(ApiRequestHeaders.X_PROFILE_ID, String.valueOf(profileId));
        return headers;
    }

    public Map<String, String> extractHeaders(HttpServletRequest request){
        Map<String, String> headers = new HashMap<>();
        headers.put(X_API_KEY, request.getHeader(X_API_KEY));
        headers.put(X_API_CLIENT_ID, request.getHeader(X_API_CLIENT_ID));
        headers.put(X_USER_ID, request.getHeader(X_USER_ID) !=null ? request.getHeader(X_USER_ID) : String.valueOf(-1));
        headers.put(X_STAFF_ID, request.getHeader(X_STAFF_ID) != null ? request.getHeader(X_STAFF_ID) : String.valueOf(-1));
        headers.put(X_REQUEST_ID, request.getHeader(X_REQUEST_ID) !=null ? request.getHeader(X_REQUEST_ID) : String.valueOf(-1) );
        headers.put(X_PROFILE_ID, request.getHeader(X_PROFILE_ID) !=null ? request.getHeader(X_PROFILE_ID) : String.valueOf(-1));
        return headers;
    }

}
