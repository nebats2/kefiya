package com.kefiya.home.entities.arifpay;

import com.kefiya.home.enums.TransactionStatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name ="arifpay_order")
@Data
public class ArifpayOrderEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long Id;

    @Column(nullable = false)
    String mobile;

    @Column(nullable = false)
    Long profileId;

    @Column(nullable = false)
    String serviceId;

    @Column(nullable = false)
    String serviceName;

    @Column(nullable = false)
    Float amountInBirr;

    @Column(nullable = false, unique = true)
    String referenceId;

    @Column(nullable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime createdDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    TransactionStatusEnum status;
}
