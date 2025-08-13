package com.kefiya.home.services;

import com.kefiya.home.database.entities.ProductEntity;
import com.kefiya.home.database.repos.ProductRepo;
import com.kefiya.home.enums.ProductCategory;
import com.kefiya.home.models.ProductCreateModel;
import com.kefiya.home.models.ProductOutModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepo productRepo;

    @InjectMocks
    private ProductService productService;

    private ProductCreateModel sampleCreateModel() {
        var m = new ProductCreateModel();
        m.setTitle("Jimma Coffee");
         m.setCategory(ProductCategory.COFFEE); // if needed in your project
        m.setStock(10);
        m.setPrice(25.50);
        return m;
    }


    @Test
    @DisplayName("create success")
    void create_success() {
        when(productRepo.save(any(ProductEntity.class)))
                .thenAnswer(inv -> {
                    ProductEntity e = inv.getArgument(0);
                    if (e.getId() == null) e.setId(1L);
                    return e;
                })
                .thenAnswer(inv -> inv.getArgument(0));

        ProductOutModel out = productService.create(sampleCreateModel());

        assertNotNull(out);
    }

    @Test
    @DisplayName("check duplicate product title : throw data violation exception")
    void create_dataIntegrityViolation() {
        when(productRepo.save(any(ProductEntity.class)))
                .thenAnswer(inv -> {
                    ProductEntity e = inv.getArgument(0);
                    e.setTitle("Jimma Coffee");
                    return e;
                })
                .thenThrow(new DataIntegrityViolationException("duplicate"));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> productService.create(sampleCreateModel())
        );
        assertTrue(ex.getMessage().toLowerCase().contains("violation"));
    }

}
