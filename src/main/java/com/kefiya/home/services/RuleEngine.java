package com.kefiya.home.services;

import com.kefiya.home.database.repos.ProductRepo;
import com.kefiya.home.database.repos.PromotionRepo;
import com.kefiya.home.enums.PromotionTypes;
import com.kefiya.home.models.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RuleEngine {
    private final PromotionRepo promotionRepo;
    private final ProductRepo productRepo;
    /**
    // This is a mere assumption, in fact, persisted rules with order and priority is a better solution
        1st. Item - Buy x get y free : filter free if for each x items there exists a y product in the cart list : the cart must contain the y-fee
        2nd. Item - Discount by product category : calculate each items if product category discount promo exists
        3rd. Cart - Bulk amount discount : calculate bulk and final price if bulk discount promo exisits

     **/
    public CartResponse applyRule(CartRequest cartRequest){
        var cartResponse = applyItemRules(cartRequest);
        applyCartRules(cartResponse);
        return cartResponse;
    }


    private void applyCartRules(CartResponse cartResponse){
        var itemCartResponses =  cartResponse.getItems();
        var cartFinalPrice = itemCartResponses.stream().mapToDouble(ItemCartResponse::getFinalPrice).sum();
        var cartTotalPrice = itemCartResponses.stream().mapToDouble(ItemCartResponse::getTotalPrice).sum();

        cartResponse.setFinalPrice(cartFinalPrice);
        cartResponse.setTotalPrice(cartTotalPrice);

        // rurles at the cart level
        applyBulkDiscount(cartResponse);
    }

    private CartResponse applyItemRules(CartRequest cartRequest){
        if(cartRequest == null){
            throw new IllegalArgumentException("no quote found");
        }
        var items = cartRequest.getItems();

        CartResponse cartResponse =  new CartResponse();
        cartResponse.setIdempotencyKey(cartRequest.getIdempotencyKey());

        List<ItemCartResponse> itemCartResponses = new ArrayList<>();

        for(var item : items){
            var itemResponse = new ItemCartResponse();
            var product = productRepo.findById(item.getProductId()).orElseThrow(
                    () -> new EntityNotFoundException("Product y is not found")
            );
            itemResponse.setItem(item);
            itemResponse.setTotalPrice(product.getPrice() * item.getQty());
            itemResponse.setFinalPrice(product.getPrice() * item.getQty());
            itemResponse.setAppliedItemPromotion(new ArrayList<>());

            applyItemRules(itemResponse);

            itemCartResponses.add(itemResponse);
        }

        cartResponse.setItems(itemCartResponses);
         return cartResponse;
    }

    public void applyBulkDiscount(CartResponse cartResponse) {
        var bulkDiscountPromo = promotionRepo.findByType(PromotionTypes.BULK_DISCOUNT);
        if(bulkDiscountPromo != null){
            var finalPrice = cartResponse.getFinalPrice();
            if(finalPrice > 0){
                finalPrice = finalPrice - (finalPrice * bulkDiscountPromo.getBulkDiscountPerc())/100.00;
                cartResponse.setFinalPrice(finalPrice);
            }
            cartResponse.setAppliedOrderPromotion(List.of(PromotionOutModel.buildPromoOut(bulkDiscountPromo)));
        }
    }


    public void applyItemRules(ItemCartResponse itemCartResponse) {
        var item = itemCartResponse.getItem();
        var product = productRepo.findById(item.getProductId()).orElseThrow(
                () -> new EntityNotFoundException("Product y is not found")
        );
        var price = item.getQty() * product.getPrice();
        itemCartResponse.setItem(item);
        itemCartResponse.setFinalPrice(price);

        // apply rules  by order : we can add rules as a chain
        applyBuyXFreeYRule(itemCartResponse);
        applyCategoryDiscountRule(itemCartResponse);

    }


    private void applyBuyXFreeYRule(ItemCartResponse itemCartResponse) {
        var freeYPromo = promotionRepo.findByType(PromotionTypes.BUY_X_GET_Y);
        List<PromotionOutModel> appliedPromotions = itemCartResponse.getAppliedItemPromotion();

        if (freeYPromo != null && freeYPromo.getFreeYProduct().getId().equals(itemCartResponse.getItem().getProductId())) {
            appliedPromotions.add(PromotionOutModel.buildPromoOut(freeYPromo));
            itemCartResponse.setFinalPrice(0.0);
            itemCartResponse.setAppliedItemPromotion(appliedPromotions);
        }

    }

    private void applyCategoryDiscountRule(ItemCartResponse itemCartResponse) {
        var item = itemCartResponse.getItem();
        var product = productRepo.findById(item.getProductId()).orElseThrow(
                () -> new EntityNotFoundException("Product y is not found")
        );
        var catDiscountPromo = promotionRepo.findByDiscountCategory(product.getCategory());
        List<PromotionOutModel> appliedPromotions = itemCartResponse.getAppliedItemPromotion();


        if (catDiscountPromo != null) {
            appliedPromotions.add(PromotionOutModel.buildPromoOut(catDiscountPromo));
            var price = itemCartResponse.getFinalPrice();
            if (price > 0) {
                price = price - price * (catDiscountPromo.getDiscountCategoryPerc() / 100);
            }
            itemCartResponse.setFinalPrice(price);

        }
    }


}
