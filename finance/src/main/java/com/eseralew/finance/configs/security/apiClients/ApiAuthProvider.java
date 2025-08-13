package com.kefiya.home.configs.security.apiClients;



import com.kefiya.home.apis.appsClient.enums.ApiClientNameEnum;
import com.kefiya.home.apis.appsClient.models.ApiClient;
import com.kefiya.home.apis.appsClient.services.ApiClientService;
import com.kefiya.home.common.ApiRequestHeaders;
import com.kefiya.home.common.BaseException;
import com.kefiya.home.common.ErrorMessage;
import com.kefiya.home.configs.AppConfigConstant;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
@AllArgsConstructor
public class ApiAuthProvider implements AuthenticationProvider {
    final Logger logger = LoggerFactory.getLogger(ApiAuthProvider.class);

    private final ApiClientService apiClientService;
    private final AppConfigConstant appConfigConstant;

    @Override
    public Authentication authenticate(Authentication authentication) {
        Map<String,String> principals = (Map<String, String>) authentication.getPrincipal();

        String apiKey = principals.get(ApiRequestHeaders.X_API_KEY);
        String id = principals.get(ApiRequestHeaders.X_API_CLIENT_ID);

        try {
            ApiClient apiClient = apiClientService.getClient(Integer.parseInt(id));
            if (apiClient.getApikey().equals(apiKey) && apiClient.getId()== Integer.parseInt(id)) {

                authenticateApi(apiClient.getName().name());
                authenticateApi(principals, apiClient);


                UserDetails principal = new User(apiClient.getName().name(), "", Collections.emptyList());

                return new PreAuthenticatedAuthenticationToken(principal, "N/A", principal.getAuthorities());
            } else {

                throw new BadCredentialsException("Invalid API Key");
            }

        }  catch (Exception e) {
            logger.error(e.getMessage());

            throw new RuntimeException(e);
        }

    }

    private void authenticateApi( Map<String,String> principals, ApiClient apiClient){

        if(apiClient.getName() == ApiClientNameEnum.ESERALEW_ADMIN_AUTH) {
            if(principals.get(ApiRequestHeaders.X_STAFF_ID).equals("-1")) {
                System.out.println("staff id is not found");
                throw new BadCredentialsException("Invalid User staff id");
            }
        }
/*
        if(apiClient.getName() == ApiClientNameEnum.ESERALEW_PROFILE_AUTH) {
            if(principals.get(ApiRequestHeaders.X_PROFILE_ID).equals("-1")) {
                System.out.println("profile id is not found");
                throw new BadCredentialsException("Profile id is not found");
            }
        }*/

    }
    private void authenticateApi(String apiName) throws BaseException {
         if(!appConfigConstant.getPermittedApis().contains(apiName)){
             throw new BaseException(ErrorMessage.app_client_is_not_found, "app client has no permission");
         }
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
