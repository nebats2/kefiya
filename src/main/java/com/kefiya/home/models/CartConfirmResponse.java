package com.kefiya.home.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartConfirmResponse {

    Long orderId;
    String idempotencyKey;

    double finalPrice;


}
