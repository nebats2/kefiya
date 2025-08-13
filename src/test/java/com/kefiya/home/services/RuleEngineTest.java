package com.kefiya.home.services;
import com.kefiya.home.database.entities.ProductEntity;
import com.kefiya.home.database.entities.PromotionEntity;
import com.kefiya.home.database.repos.ProductRepo;
import com.kefiya.home.database.repos.PromotionRepo;
import com.kefiya.home.enums.ProductCategory;
import com.kefiya.home.enums.PromotionTypes;
import com.kefiya.home.models.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)

public class RuleEngineTest {
    @Mock ProductRepo productRepo;
    @Mock
    PromotionRepo promotionRepo;
    @InjectMocks RuleEngine ruleEngine;
    @InjectMocks CartService cartService;

    private ProductEntity product(long id, ProductCategory cat, double price, int stock) {
        var p = new ProductEntity();
        p.setId(id);
        p.setTitle("P" + id);
        p.setCategory(cat);
        p.setPrice(price);
        p.setStock(stock);
        return p;
    }

    private PromotionEntity bulk(double percent) {
        var pe = new PromotionEntity();
        pe.setType(PromotionTypes.BULK_DISCOUNT);
        pe.setEnabled(true);
        pe.setBulkDiscountPerc(percent);
        return pe;
    }

    private PromotionEntity catDisc(ProductCategory cat, double percent) {
        var pe = new PromotionEntity();
        pe.setType(PromotionTypes.PERCENTAGE_OFF_CATEGORY);
        pe.setEnabled(true);
        pe.setDiscountCategory(cat);
        pe.setDiscountCategoryPerc(percent);
        return pe;
    }

    private PromotionEntity bxgy(ProductEntity freeY) {
        var pe = new PromotionEntity();
        pe.setType(PromotionTypes.BUY_X_GET_Y);
        pe.setEnabled(true);
        pe.setFreeYProduct(freeY);
        return pe;
    }



    private ProductEntity product(long id, ProductCategory cat, double price) {
        var p = new ProductEntity();
        p.setId(id);
        p.setTitle("P" + id);
        p.setCategory(cat);
        p.setPrice(price);
        p.setStock(999);
        return p;
    }

    private ItemCartRequest item(long productId, int qty) {
        var it = new ItemCartRequest();
        it.setProductId(productId);
        it.setQty(qty);
        return it;
    }

    private ItemCartResponse itemRes(long productId, int qty, double total, double fin) {
        var r = new ItemCartResponse();
        r.setItem(item(productId, qty));
        r.setTotalPrice(total);
        r.setFinalPrice(fin);
        r.setAppliedItemPromotion(List.of(new PromotionOutModel()));
        return r;
    }

    private CartRequest cartRequest(String key, List<ItemCartRequest> items) {
        CartRequest r = new CartRequest();
        r.setIdempotencyKey(key);
        r.setItems(items);
        return r;
    }

    @Test
    @DisplayName("applyRule: BUY_X_GET_Y + Category % + Bulk % → correct final prices & promos")
    void applyRule_combinedPromotions() {
        // Products
        var p1 = product(1L, ProductCategory.ELECTRONICS, 100.0, 100);
        var p2 = product(2L, ProductCategory.COFFEE, 50.0, 100);

        // Repos for item lookups in RuleEngine
        when(productRepo.findById(1L)).thenReturn(Optional.of(p1));
        when(productRepo.findById(2L)).thenReturn(Optional.of(p2));

        // Promotions
        when(promotionRepo.findByType(PromotionTypes.BULK_DISCOUNT))
                .thenReturn(bulk(5.0));  // 5% on cart final

        when(promotionRepo.findByType(PromotionTypes.BUY_X_GET_Y))
                .thenReturn(bxgy(p2));   // product 2 is free Y

        when(promotionRepo.findByDiscountCategory(ProductCategory.ELECTRONICS))
                .thenReturn(catDisc(ProductCategory.ELECTRONICS, 10.0)); // -10% item A

        when(promotionRepo.findByDiscountCategory(ProductCategory.COFFEE))
                .thenReturn(null); // no category promo for B

        // Cart request: A x2, B x1
        var i1 = new ItemCartRequest();
        i1.setProductId(1L); i1.setQty(2);
        var i2 = new ItemCartRequest();
        i2.setProductId(2L); i2.setQty(1);

        var cartReq = new CartRequest();
        cartReq.setIdempotencyKey("IDEMP-1");
        cartReq.setItems(List.of(i1, i2));

        // Act
        CartResponse res = ruleEngine.applyRule(cartReq);

        // Assert totals
        assertThat(res.getTotalPrice()).isEqualTo(250.0); // 200 + 50
        assertThat(res.getFinalPrice()).isEqualTo(171.0); // (200*0.9 + 0) * 0.95 = 171

        // Assert item-level
        List<ItemCartResponse> items = res.getItems();

        var itemA = items.stream().filter(x -> x.getItem().getProductId().equals(1L)).findFirst().orElseThrow();
        var itemB = items.stream().filter(x -> x.getItem().getProductId().equals(2L)).findFirst().orElseThrow();

        assertThat(itemA.getTotalPrice()).isEqualTo(200.0);
        assertThat(itemA.getFinalPrice()).isEqualTo(180.0); // 10% off

        assertThat(itemB.getTotalPrice()).isEqualTo(50.0);
        assertThat(itemB.getFinalPrice()).isEqualTo(0.0);   // free Y

        // Order-level promotions (bulk)
        assertThat(res.getAppliedOrderPromotion()).isNotNull();
    }


}
