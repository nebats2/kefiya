package com.kefiya.home.models;

import com.kefiya.home.database.entities.PromotionEntity;
import com.kefiya.home.enums.ProductCategory;
import com.kefiya.home.enums.PromotionTypes;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class PromotionOutModel  {

    Long id;
    PromotionTypes type;
    ProductOutModel buyXProduct ;
    ProductOutModel freeYProduct ;

    Integer buyXMinQty ;
    Integer freeYQty;

    Double bulkDiscountMinAmt ;
    Double bulkDiscountPerc;

    ProductCategory discountCategory;
    Double  discountCategoryPerc;

    @Column(nullable = false)
    Boolean enabled = true;

    public static PromotionOutModel buildPromoOut(PromotionEntity entity ){
        var out = new PromotionOutModel ();
        out.setId(entity.getId());
        out.setType(entity.getType());

        if(entity.getBuyXProduct() !=null) {
            out.setBuyXProduct(ProductOutModel.buildOut(entity.getBuyXProduct()));
        }
        if(entity.getFreeYProduct() != null){
            out.setFreeYProduct(ProductOutModel.buildOut(entity.getFreeYProduct()));
        }
        out.setBuyXMinQty(entity.getBuyXMinQty());

        out.setFreeYQty(entity.getFreeYQty());
        out.setBulkDiscountPerc(entity.getBulkDiscountPerc());
        out.setBulkDiscountMinAmt(entity.getBulkDiscountMinAmt());

        out.setDiscountCategory(entity.getDiscountCategory());
        out.setDiscountCategoryPerc(entity.getDiscountCategoryPerc());

        out.setEnabled(entity.getEnabled());
        return out;
    }
}
