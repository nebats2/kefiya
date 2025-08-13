package com.kefiya.home.models;

import com.kefiya.home.entities.arifpay.ArifpayOrderEntity;
import com.kefiya.home.enums.TransactionStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CheckoutModel {
    String mobile;

    Long profileId;
    String apiRequestId;
    ItemModel items;

    public static ArifpayOrderEntity build(CheckoutModel model){
        var entity = new ArifpayOrderEntity();
        entity.setAmountInBirr(model.getItems().getPrice() * model.getItems().getQuantity());
        entity.setMobile(model.getMobile());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setStatus(TransactionStatusEnum.PENDING);
        entity.setReferenceId(model.getApiRequestId());
        entity.setProfileId(model.getProfileId());
        entity.setServiceId(model.getItems().getName());
        entity.setServiceName(model.getItems().getName());
        return entity;

    }

}
