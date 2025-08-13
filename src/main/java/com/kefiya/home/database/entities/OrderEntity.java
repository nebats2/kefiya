package com.kefiya.home.database.entities;

import com.kefiya.home.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="cart_order")
@Data
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String idempotencyKey;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    OrderStatus status;

    double totalPrice;
    double finalPrice;

}
