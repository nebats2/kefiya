package com.kefiya.home.controllers;

import com.kefiya.home.common.BaseException;
import com.kefiya.home.common.BaseResponseEntity;
import com.kefiya.home.common.ErrorMessage;
import com.kefiya.home.models.CheckoutModel;
import com.kefiya.home.services.arifpay.ArifPayPaymentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final ArifPayPaymentService arifPayPaymentService;

    @PostMapping("/checkout")
    public BaseResponseEntity<Object> checkout(@Valid @RequestBody CheckoutModel model, BindingResult bindingResult) throws BaseException {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors().get(0).getDefaultMessage();
            throw new BaseException(ErrorMessage.bad_request, errors);
        }
        return new BaseResponseEntity<>(arifPayPaymentService.receivePayment(model), HttpStatus.OK);
    }



}
