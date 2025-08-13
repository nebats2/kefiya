package com.kefiya.home.views;

import com.kefiya.home.common.BaseException;
import com.kefiya.home.configs.ArifPaySettings;
import com.kefiya.home.configs.GojjebneasAccountSettings;
import com.kefiya.home.entities.arifpay.ArifPayClientEntity;
import com.kefiya.home.entities.arifpay.ArifpayOrderEntity;
import com.kefiya.home.enums.PaymentMethod;
import com.kefiya.home.models.CheckoutModel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class RequestArifPayCheckout {
    String cancelUrl;
    String phone;
    String email;
    String nonce;
    String successUrl;
    String errorUrl;
    String notifyUrl;
    List<PaymentMethod> paymentMethods;
    LocalDateTime expireDate;

    List<RequestArifPayItem> items;
    List<RequestArifPayBeneficiary> beneficiaries;
    String lang = "EN";


    public static RequestArifPayCheckout build(CheckoutModel model, ArifpayOrderEntity order, ArifPaySettings arifPaySettings, GojjebneasAccountSettings companySettings){

            var item =  model.getItems();
            var itemPrice = item.getPrice();
            var quantity =  item.getQuantity();

            var totalAmount = itemPrice * quantity;

            final String nonce = order.getReferenceId();

            var request = new RequestArifPayCheckout();
            request.setCancelUrl(arifPaySettings.getCancelUrl());
            request.setNotifyUrl(arifPaySettings.getNotifyUrl());
            request.setErrorUrl(arifPaySettings.getErrorUrl());
            request.setSuccessUrl(arifPaySettings.getSuccessUrl());
            request.setNonce(UUID.randomUUID().toString());

            request.setEmail(companySettings.getEmail());
            request.setPhone(companySettings.getMobile());
            request.setNonce(nonce);

            var beneficiary =  RequestArifPayBeneficiary.beneficiaries(totalAmount, companySettings);
            request.setBeneficiaries(beneficiary);

            request.setItems(List.of(RequestArifPayItem.build(model.getItems())));

            return request;
    }
}
