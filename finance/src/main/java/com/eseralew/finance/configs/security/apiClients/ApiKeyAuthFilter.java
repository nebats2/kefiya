package com.kefiya.home.configs.security.apiClients;

import com.kefiya.home.apis.appsClient.services.ApiClientService;
import com.kefiya.home.common.ApiRequestHeaders;
import com.kefiya.home.common.BaseException;
import com.kefiya.home.services.controllers.ContextService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import java.io.IOException;
import java.util.Map;
@AllArgsConstructor
public class ApiKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter {
    private final ApiClientService apiClientService;
    private final ContextService contextService;
    private final ApiRequestHeaders apiRequestHeaders;



    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        try {
            return apiRequestHeaders.extractHeaders(request);
        }catch (Exception ex){
            throw new BadCredentialsException("invalid headers");
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String ipAddress = httpRequest.getRemoteAddr();

        String path = httpRequest.getRequestURI();
        logger.info("request "+path+ "{} IP:"+ipAddress);


        try {
            var principal = (Map<String, String>) getPreAuthenticatedPrincipal(httpRequest);
            if (principal == null) {
                logger.error("client not found");
                throw new BadCredentialsException("client not found");

            }
            var client = apiClientService.getClient(Integer.parseInt(principal.get(ApiRequestHeaders.X_API_CLIENT_ID)));
            if(!client.getApikey().equals(principal.get(ApiRequestHeaders.X_API_KEY))) {
                throw new BadCredentialsException("client not found");
            }
            contextService.setApiClientName(client.getName());
            contextService.setRequestedUserId(Long.parseLong(principal.get(ApiRequestHeaders.X_USER_ID)));
            contextService.setStaffId(Long.parseLong(principal.get(ApiRequestHeaders.X_STAFF_ID)));
            contextService.setProfileId(Long.parseLong(principal.get(ApiRequestHeaders.X_PROFILE_ID)));
            super.doFilter(request, response, chain);

        } catch (BadCredentialsException ex) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());

        }catch (RuntimeException | BaseException e){
            e.printStackTrace();
            logger.error(e.getMessage());
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "not authorized");
        }
    }
    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }
}