package com.kefiya.home.entities.arifpay;

import com.kefiya.home.enums.TransactionStatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name ="arifpay_trx_status")
@Data
public class ArifPayTrxStatusEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long Id;

    @OneToOne
    @JoinColumn(nullable = false, unique = true, name = "order_id")
    ArifpayOrderEntity order;

    @Column(nullable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime createdDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    TransactionStatusEnum status;

    @Column(nullable = false)
    String sessionId;
}
