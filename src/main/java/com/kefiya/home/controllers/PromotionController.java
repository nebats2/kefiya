package com.kefiya.home.controllers;

import com.kefiya.home.models.*;
import com.kefiya.home.services.ControllerServices;
import com.kefiya.home.services.PromotionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/promo")
public class PromotionController {
    private final PromotionService promotionService;

    @PostMapping("/create")
    @Operation(summary = "create promotion",
            description = "`BUY_X_GET_Y` type free y can't be less than 1.")
    public ResponseEntity<PromotionOutModel> create(@Valid @RequestBody PromotionCreateModel createModel, BindingResult bindingResult ){
        ControllerServices.checkBindingResult(bindingResult);
        return ResponseEntity.ok(promotionService.create(createModel));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<PromotionOutModel> detail(@PathVariable("id") Long id){

        return ResponseEntity.ok(promotionService.get(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<PromotionOutModel>> list( ){

        return ResponseEntity.ok(promotionService.listAll());
    }
}
