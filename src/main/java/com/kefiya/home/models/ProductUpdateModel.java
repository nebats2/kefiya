package com.kefiya.home.models;

import com.kefiya.home.database.entities.ProductEntity;
import com.kefiya.home.enums.ProductCategory;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductUpdateModel {

    @NotNull(message = "Product id can't be empty")
    Long id;

    @NotNull(message = "Product title can't be empty")
    String title;

    @NotNull(message = "Product Category can't be empty")
    ProductCategory category;

    int stock;

    double price;


    public static ProductEntity updateEntity(ProductEntity entity, ProductUpdateModel updateModel){
         entity.setPrice(updateModel.getPrice());
         entity.setTitle(updateModel.getTitle());
         entity.setCategory(updateModel.getCategory());
         entity.setStock(updateModel.getStock());
         entity.setPrice(updateModel.getPrice());
         return  entity;
    }
}
