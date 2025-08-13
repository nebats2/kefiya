package com.kefiya.home.services.arifpay;

import com.kefiya.home.common.BaseException;
import com.kefiya.home.entities.arifpay.ArifPayOrderRepo;
import com.kefiya.home.models.CheckoutModel;
import com.kefiya.home.views.ArifPayResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ArifPayPaymentService {
    private final ArifPayOrderRepo orderRepo;
    private final ArifPayRestService restService;

    @Transactional
    public HttpStatusCode receivePayment(CheckoutModel model) throws BaseException {

        try {
            var order = CheckoutModel.build(model);
            order = orderRepo.save(order);
            log.info("customer with profile id {} amount {} item {} created", model.getProfileId(), model.getItems().getName(),
                    model.getItems().getPrice());
            return restService.checkOutSession(model, order);

        }catch (BaseException ex){
            log.error(" exception :{}", ex.getMessage());
            throw ex;
        }

    }
}
