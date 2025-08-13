package com.kefiya.home.models;

import com.kefiya.home.enums.CustomerSegment;
import com.kefiya.home.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CartRequest {

    @NotNull(message = "customer segment can't be empty")
    CustomerSegment customerSegment;

    @NotNull(message = "items can't be empty")
    @NotEmpty(message = "items can't be empty")
    List<ItemCartRequest> items;

    @NotNull(message = "order key can't be empty")
    String idempotencyKey;

}
