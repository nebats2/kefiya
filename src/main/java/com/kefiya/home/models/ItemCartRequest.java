package com.kefiya.home.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ItemCartRequest {

    @NotNull(message = "product id can't be empty")
    Long productId;

    @NotNull(message = "quantity can't be empty")
    @Positive(message = "quantity must be greater than zero")
    Integer qty;

}
