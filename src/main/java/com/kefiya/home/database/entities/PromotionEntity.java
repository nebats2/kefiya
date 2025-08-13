package com.kefiya.home.database.entities;

import com.kefiya.home.enums.ProductCategory;
import com.kefiya.home.enums.PromotionTypes;
import com.kefiya.home.models.PromotionCreateModel;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name ="promotion",
        uniqueConstraints = {
@UniqueConstraint(
        name = "uk_buyx_freey_enabled",
        columnNames = {"x_product_id", "y_product_id", "enabled"}
)
  })
@Data
public class PromotionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)  // to avoid duplicate confusion without the need for further rules and checkups
    PromotionTypes type;

    @ManyToOne
    @JoinColumn(name = "x_product_id")
    ProductEntity buyXProduct;

    @ManyToOne
    @JoinColumn(name = "y_product_id")
    ProductEntity freeYProduct;

    Integer buyXMinQty ;
    Integer freeYQty;

    Double bulkDiscountMinAmt ;
    Double bulkDiscountPerc;

    ProductCategory discountCategory;
    Double  discountCategoryPerc;

    @Column(nullable = false)
    Boolean enabled = true;

    public void setBulkDiscountMinAmt(Double bulkDiscountMinAmt){
        if(bulkDiscountMinAmt == null){
            this.bulkDiscountMinAmt = null;
        }else{
            this.bulkDiscountMinAmt = Math.round(bulkDiscountMinAmt * 100.0) /100.0;
        }
    }

    public PromotionEntity build(PromotionCreateModel createModel){
        this.setType(createModel.getType());
        return buildBuyXGetY(createModel)
                .buildBulkDiscount(createModel)
                .buildPercentageOffCategory(createModel);
    }


    private PromotionEntity buildBuyXGetY(PromotionCreateModel createModel){
        if(createModel.getType() == PromotionTypes.BUY_X_GET_Y){
            if(this.buyXProduct == null || this.freeYProduct == null){
                throw new IllegalArgumentException("Product x and y can't be empty for BUY_X_GET_Y type");
            }

            if(createModel.getFreeYQty() == null || createModel.getFreeYQty() <=0){
                throw new IllegalArgumentException("Product y free quantity can't be empty/zero for BUY_X_GET_Y type");
            }
            this.setBuyXMinQty(createModel.getBuyXMinQty() == null ?1 : createModel.getBuyXMinQty());
        }
        return this;
    }

    private PromotionEntity buildBulkDiscount( PromotionCreateModel createModel){
        if(createModel.getType() == PromotionTypes.BULK_DISCOUNT){

            if(createModel.getBulkDiscountPerc() == null || createModel.getBulkDiscountPerc() <=0 || createModel.getBulkDiscountPerc() >=99.9){
                throw new IllegalArgumentException("Bulk discount percentage can't be empty for BULK_DISCOUNT type");
            }
            this.setBulkDiscountPerc(createModel.getBulkDiscountPerc());
            this.setBulkDiscountMinAmt(createModel.getBulkDiscountMinAmt() == null ?0.0 : createModel.getBulkDiscountMinAmt());

        }
        return this;
    }
    private PromotionEntity buildPercentageOffCategory( PromotionCreateModel createModel){
        if(createModel.getType() == PromotionTypes.PERCENTAGE_OFF_CATEGORY){
            if(createModel.getDiscountCategory() == null || createModel.getDiscountCategoryPerc() == null){
                throw new IllegalArgumentException("Discounted category and percentage can't be emmpty for BUY_X_GET_Y type");
            }
            if(createModel.getDiscountCategoryPerc() <=0 || createModel.getDiscountCategoryPerc()>=99.9){
                throw new IllegalArgumentException("invalid Dicsount percentage");
            }
            this.setDiscountCategory(createModel.getDiscountCategory());
            this.setDiscountCategoryPerc(createModel.getDiscountCategoryPerc());
        }
        return this;
    }
}
