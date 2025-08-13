package com.kefiya.home.models;

import com.kefiya.home.enums.ProductCategory;
import com.kefiya.home.enums.PromotionTypes;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PromotionCreateModel {
    @NotNull(message = "promo type can't be empty")
    PromotionTypes type;

    Long buyXProductId;
    Long freeYProductId;

    Integer buyXMinQty ;
    Integer freeYQty;

    Double bulkDiscountMinAmt ;
    Double bulkDiscountPerc;

    ProductCategory discountCategory;
    Double  discountCategoryPerc;

}
