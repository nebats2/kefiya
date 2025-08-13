package com.kefiya.home.entities.arifpay;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name ="arifpay_client")
@Data
public class ArifPayClientEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long Id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String merchant_id;

    @Column(nullable = false)
    String apiKey;

    @Column(nullable = false)
    String prKey;

    @Column(nullable = false)
    String pbKey;

    @Column(nullable = false)
    Boolean enabled;


    String paymentMethods;
}
