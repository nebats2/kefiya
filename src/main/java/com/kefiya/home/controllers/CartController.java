package com.kefiya.home.controllers;

import com.kefiya.home.models.CartConfirmResponse;
import com.kefiya.home.models.CartRequest;
import com.kefiya.home.models.CartResponse;
import com.kefiya.home.services.CartService;
import com.kefiya.home.services.ControllerServices;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/quote")
    public ResponseEntity<CartResponse> create(@Valid @RequestBody CartRequest cartRequest, BindingResult bindingResult ){
        ControllerServices.checkBindingResult(bindingResult);
        return ResponseEntity.ok(cartService.createQuote(cartRequest));
    }

    @PostMapping("/confirm")
    public ResponseEntity<CartConfirmResponse> confirm(@Valid @RequestBody CartResponse cartResponse, BindingResult bindingResult ){
        ControllerServices.checkBindingResult(bindingResult);
        return ResponseEntity.ok(cartService.confirmCart(cartResponse));
    }
}
