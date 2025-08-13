package com.kefiya.home.apiRestService;

import com.kefiya.home.common.ApiRequestHeaders;
import com.kefiya.home.common.BaseException;
import com.kefiya.home.common.BaseResponseEntity;
import com.kefiya.home.services.controllers.ContextService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class RestTemplateService {

    private final RestTemplate restTemplate;
    private final ContextService contextService;

    public HttpHeaders getHeader() {
        var requestedUserId = contextService.getRequestedUserId() != null ? contextService.getRequestedUserId() : -1;

        // Create and populate headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(ApiRequestHeaders.X_USER_ID, String.valueOf(requestedUserId));
        return headers;
    }

    public BaseResponseEntity postAsync(Map<String, List<String>> headers, String host, String path, Object request, Class<?> requestClass) throws BaseException {
        log.info("API calls in path : " + host + path);

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::put);

        HttpEntity<Object> httpEntity = new HttpEntity<>(request, httpHeaders);
        return call(host, path, httpEntity);
    }

    public BaseResponseEntity post(String host, String path, Object request, Class<?> requestClass) throws BaseException {
        log.info("API calls in path : " + host + path);

        HttpHeaders headers = getHeader();
        HttpEntity<Object> httpEntity = new HttpEntity<>(request, headers);
        return call(host, path, httpEntity);
    }

    public BaseResponseEntity post(String host, String path, Class<?> requestClass) throws BaseException {
        log.info("API calls in path : " + host + path);

        HttpHeaders headers = getHeader();
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        return call(host, path, httpEntity);
    }

    public BaseResponseEntity post(Map<String, List<String>> headers, String host, String path, Object request, Class<?> requestClass) throws BaseException {
        log.info("API calls in path : " + host + path);

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::put);

        HttpEntity<Object> httpEntity = new HttpEntity<>(request, httpHeaders);

        return call(host, path, httpEntity);
    }

    private   BaseResponseEntity call(String host, String path, HttpEntity httpEntity) throws BaseException {
        try {
            ResponseEntity<BaseResponseEntity> response = restTemplate.exchange(
                    host + path,
                    HttpMethod.POST,
                    httpEntity,
                    BaseResponseEntity.class
            );
            log.info("API call successful with response: " + response.getBody());
            return response.getBody();

        } catch (Exception e) {
            log.error("Error during API call: " + e.getMessage());
            throw new BaseException("System is not available");
        }
    }


}
