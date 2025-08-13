package com.kefiya.home.services;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class ControllerServices {
    public static void checkBindingResult(BindingResult bindingResult) throws IllegalArgumentException {
        if(bindingResult.hasErrors() ) {
            var errors = bindingResult.getAllErrors().get(0).getDefaultMessage();
            throw new IllegalArgumentException(errors );
        }
    }

}
