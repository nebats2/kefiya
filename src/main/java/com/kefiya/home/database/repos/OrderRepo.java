package com.kefiya.home.database.repos;

import com.kefiya.home.database.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity, Long> {

    OrderEntity findByIdempotencyKey(String key);
}
