package com.kefiya.home.models;

import lombok.Data;

@Data
public class ItemModel {
    Long serviceId;
    String name;
    Integer quantity;
    Float price;
    String description;
}
