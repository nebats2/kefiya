package com.kefiya.home.models;

import lombok.Data;

import java.util.List;

@Data
public class CartResponse {
    List<ItemCartResponse> items;
    String idempotencyKey;

    List<PromotionOutModel> appliedOrderPromotion;
    double totalPrice;
    double finalPrice;
}
