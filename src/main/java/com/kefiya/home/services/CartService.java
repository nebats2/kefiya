package com.kefiya.home.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kefiya.home.database.entities.OrderEntity;
import com.kefiya.home.database.entities.ProductEntity;
import com.kefiya.home.database.repos.OrderRepo;
import com.kefiya.home.database.repos.ProductRepo;
import com.kefiya.home.enums.OrderStatus;
import com.kefiya.home.models.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CartService {

    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final RuleEngine ruleEngine;
    private final ObjectMapper objectMapper;

    @Transactional
    public CartConfirmResponse confirmCart(CartResponse cartResponse){
        var order = orderRepo.findByIdempotencyKey(cartResponse.getIdempotencyKey());
        if(order == null){
            throw new EntityNotFoundException("order is not found");
        }
        order.setStatus(OrderStatus.CONFIRMED);
        for(var item : cartResponse.getItems()){
            updateProductStock(item.getItem().getProductId(), item.getItem().getQty());
        }

        log.info("cart confirmed successfully");
        return new CartConfirmResponse(
                order.getId(), order.getIdempotencyKey(), order.getFinalPrice()
        );
    }

    public  CartResponse createQuote(CartRequest cartRequest) throws JsonProcessingException {
        var existingOrder = checkIdempotencyKey(cartRequest.getIdempotencyKey());
        if(existingOrder != null) {
            log.info("existing order returned with similar idempotency");
            return existingOrder;
        }

        var products = productRepo.findAllById(cartRequest.getItems().stream().map(ItemCartRequest::getProductId).toList());
        if (products.isEmpty()) {
            throw new IllegalArgumentException("items are not available in the cart");
        }
        checkProductStock(cartRequest, products);
        var order = new OrderEntity();
        order.setStatus(OrderStatus.PENDING);
        order.setIdempotencyKey(cartRequest.getIdempotencyKey());
        order.setCreatedDate(LocalDateTime.now());


        var cartResponse =  ruleEngine.applyRule(cartRequest);
        var prettyWriter =  objectMapper.writerWithDefaultPrettyPrinter();
        order.setFinalPrice(cartResponse.getFinalPrice());
        order.setTotalPrice(cartResponse.getTotalPrice());
        order.setCartResponse(prettyWriter.writeValueAsString(cartResponse));
        order = saveOrUpdate(order);
        return cartResponse;

    }

    private CartResponse checkIdempotencyKey(String idempotencyKey) throws JsonProcessingException {
            var existingResponse = orderRepo.findByIdempotencyKey(idempotencyKey);
            if(existingResponse != null){
               return objectMapper.readValue(existingResponse.getCartResponse(), CartResponse.class);
            }
            return null;
    }

    private void updateProductStock(Long productId, int deductableQty){
        var product = productRepo.findById(productId).orElseThrow(
                () -> new EntityNotFoundException("Product is not found")
        );

        if(product.getStock() < deductableQty){
            throw new IllegalArgumentException("insufficient inventory");
        }

        product.setStock(product.getStock() - deductableQty);
        productRepo.save(product);
    }

    private OrderEntity saveOrUpdate(OrderEntity order){
        try {
            order = orderRepo.save(order);
            log.info("order id :{} update/created successfully", order.getId());
            return order;

        } catch (DataIntegrityViolationException ex) {
            log.error("Failed to create/update order: {}", ex.getMessage());
            throw new IllegalArgumentException("violation of data integration. Refer manual");   //limiting exception details to client
        }
    }

    private void checkProductStock(CartRequest cartRequest, List<ProductEntity> products){
        if(cartRequest.getItems().isEmpty()){
            throw new IllegalArgumentException("items are not available in the cart");
        }
        for(var item : cartRequest.getItems()) {
            var product = products.stream().filter(p->p.getId().equals(item.getProductId())).findFirst().orElse(null);
            if (product == null || product.getStock() <= item.getQty()) {
                throw new IllegalArgumentException("product stock is not available");
            }
        }
    }

}
