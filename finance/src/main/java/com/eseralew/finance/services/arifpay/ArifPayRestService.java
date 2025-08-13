package com.kefiya.home.services.arifpay;

import com.kefiya.home.common.*;
import com.kefiya.home.configs.ArifPaySettings;
import com.kefiya.home.configs.GojjebneasAccountSettings;
import com.kefiya.home.entities.arifpay.ArifPayClientEntity;
import com.kefiya.home.entities.arifpay.ArifPayClientRepo;
import com.kefiya.home.entities.arifpay.ArifpayOrderEntity;
import com.kefiya.home.enums.TransactionStatusEnum;
import com.kefiya.home.models.CheckoutModel;
import com.kefiya.home.views.ArifPayResponse;
import com.kefiya.home.views.RequestArifPayCheckout;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Slf4j
public class ArifPayRestService {

    private final ArifPayClientRepo arifPayClientRepo;
    private final ArifPaySettings arifPaySettings;
    private final GojjebneasAccountSettings companySettings;
    private final RestTemplate restTemplate;

    public HttpStatusCode checkOutSession(CheckoutModel model, ArifpayOrderEntity order) throws BaseException {
        var arifPayClient = getClient();
        var checkoutRequest = RequestArifPayCheckout.build(model, order, arifPaySettings, companySettings);
        var baseUrl =  arifPaySettings.getBaseUrl();
        var checkoutUrl =  arifPaySettings.getCheckoutUrl();

        var statusCode = postCheckout(checkoutUrl, checkoutRequest);
        log.info("check out completed with status code {}", statusCode);
        return statusCode;

    }

    public HttpHeaders getHeader( ) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("x-arifpay-key", arifPaySettings.getApiKey());
        headers.add("Content-Type", "application/json");


        return headers;
    }

    private HttpStatusCode postCheckout(String path, Object requestObject ) throws BaseException {
        try {
            HttpEntity<Object> httpEntity = new HttpEntity<>(requestObject, getHeader());
            ResponseEntity<ArifPayResponse> response = restTemplate.exchange(
                    path,
                    HttpMethod.POST,
                    httpEntity,
                    ArifPayResponse.class
            );
            log.info("Arif pay api call  successful with response: {}, status code {} ", response.getBody(), response.getStatusCode());

            if(response.getBody() == null){
                log.error("arif pay api call error : null body response");
                throw new BaseException("Internal error");
            }
            return response.getStatusCode();

        } catch (Exception e) {
            log.error("Error during arifpay API call: {}", e.getMessage());
            throw new BaseException("System is not available");
        }
    }


    private ArifPayClientEntity getClient() throws BaseException {
        //var arifPayClient =  arifPayClientRepo.findById(1l).orElse(null);
        var arifPayClient =  new ArifPayClientEntity();
        arifPayClient.setId(1l);
        arifPayClient.setName("ArifPay");
       /* if(arifPayClient == null) {
            log.error("Arif pay client is not found");
            throw new BaseException(ErrorMessage.entity_is_not_found, "Arif pay is not found");
        }*/
        /*if(!arifPayClient.getEnabled()){
            log.error("Arif pay client is not active");
            throw new BaseException(ErrorMessage.entity_is_not_active, "Arif pay is not active");
        }*/

        return arifPayClient;
    }
}
