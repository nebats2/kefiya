package com.kefiya.home.entities.arifpay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArifPayOrderRepo extends JpaRepository<ArifpayOrderEntity, Long> {
}
