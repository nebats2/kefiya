package com.kefiya.home.models;

import lombok.Data;

import java.util.List;

@Data
public class ItemCartResponse {
    ItemCartRequest item ;
    List<PromotionOutModel> appliedItemPromotion;
    double totalPrice;
    double finalPrice;

}
