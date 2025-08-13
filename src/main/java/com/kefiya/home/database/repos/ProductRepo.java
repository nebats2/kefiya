package com.kefiya.home.database.repos;

import com.kefiya.home.database.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, Long> {
}
