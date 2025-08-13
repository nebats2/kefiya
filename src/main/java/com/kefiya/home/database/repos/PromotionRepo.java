package com.kefiya.home.database.repos;

import com.kefiya.home.database.entities.PromotionEntity;
import com.kefiya.home.enums.ProductCategory;
import com.kefiya.home.enums.PromotionTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepo extends JpaRepository<PromotionEntity, Long> {

    PromotionEntity findByDiscountCategory(ProductCategory discountCategory);

    PromotionEntity findByType(PromotionTypes type);
}
