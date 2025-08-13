package com.kefiya.home.models;

import com.kefiya.home.database.entities.ProductEntity;
import com.kefiya.home.enums.ProductCategory;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductCreateModel {
    String title;
    ProductCategory category;
    int stock;
    double price;

    public static ProductEntity buildEntity(ProductCreateModel model){

        var productEntity =  new ProductEntity();
        productEntity.setTitle(model.getTitle());
        productEntity.setCategory(model.getCategory());
        productEntity.setStock(model.getStock());
        productEntity.setPrice(model.getPrice());

        productEntity.setCategory(model.getCategory());
        productEntity.setCreatedDate(LocalDateTime.now());
        return productEntity;
    }
}
