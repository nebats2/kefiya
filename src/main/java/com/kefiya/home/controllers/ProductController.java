package com.kefiya.home.controllers;

import com.kefiya.home.models.ProductCreateModel;
import com.kefiya.home.models.ProductOutModel;
import com.kefiya.home.models.ProductUpdateModel;
import com.kefiya.home.services.ControllerServices;
import com.kefiya.home.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ProductOutModel> create(@Valid @RequestBody ProductCreateModel createModel, BindingResult bindingResult ){
        ControllerServices.checkBindingResult(bindingResult);
        return ResponseEntity.ok(productService.create(createModel));
    }


    @GetMapping("/detail/{id}")
    public ResponseEntity<ProductOutModel> detail(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(productService.detail(id));
    }

    @GetMapping("/list")
    @Operation(summary = "List products", description = "`pageNumber` is 0-based.")
    public ResponseEntity<Page<ProductOutModel>> list(@Param( "pageNumber") Integer pageNumber)  {
        return ResponseEntity.ok(productService.listAll(pageNumber));
    }
}
