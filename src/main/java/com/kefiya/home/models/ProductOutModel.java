package com.kefiya.home.models;

import com.kefiya.home.database.entities.ProductEntity;
import com.kefiya.home.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductOutModel {
    Long id;
    String title;
    ProductCategory category;
    int stock;
    double price;

    public static ProductOutModel buildOut(ProductEntity entity){
       return  new ProductOutModel(entity.getId(), entity.getTitle(), entity.getCategory(),entity.getStock(),
                 entity.getPrice());

    }
}
