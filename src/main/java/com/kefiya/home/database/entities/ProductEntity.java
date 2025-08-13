package com.kefiya.home.database.entities;

import com.kefiya.home.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name ="product")
@Data
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 100, unique = true)
    String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    ProductCategory category;

    @Column(nullable = false, length = 100)
    Double price;

    @Column(nullable = false,columnDefinition = "INT DEFAULT 0")
    Integer stock = 0;

    @Version
    Integer version;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime updateDate;

    public void setPrice(Double price){
        if(price == null){
            this.price = 0.0;
        }else{
            this.price = Math.round(price * 100.0) /100.0;
        }
    }

}
