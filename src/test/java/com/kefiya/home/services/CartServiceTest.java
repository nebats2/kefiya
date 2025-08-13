package com.kefiya.home.services;

import com.kefiya.home.database.entities.ProductEntity;
import com.kefiya.home.database.repos.ProductRepo;
import com.kefiya.home.enums.ProductCategory;
import jakarta.persistence.EntityNotFoundException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    @Mock
    private ProductRepo productRepo;

    @InjectMocks
    private CartService cartService;

    private ProductEntity product(Long id, int stock) {
        ProductEntity p = new ProductEntity();
        p.setId(id);
        p.setTitle("Jimma Coffee");
        p.setCategory(ProductCategory.COFFEE);
        p.setPrice(25.0);
        p.setStock(stock);
        return p;
    }

    @Test
    @DisplayName("updateProductStock - deducts and saves when stock is sufficient")
    void updateProductStock_sufficient() {
        Long productId = 1L;
        int initialStock = 10;
        int deduct = 3;
        ProductEntity p = product(productId, initialStock);

        when(productRepo.findById(productId)).thenReturn(Optional.of(p));
        when(productRepo.save(any(ProductEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        ReflectionTestUtils.invokeMethod(cartService, "updateProductStock", productId, deduct);

        assertEquals(initialStock - deduct, p.getStock());
        verify(productRepo).findById(productId);
        verify(productRepo).save(p);
    }

    @Test
    @DisplayName("update stock - throws exception stock is insufficient")
    void updateProductStock_insufficient() {
        Long productId = 2L;
        int initialStock = 2;
        int deduct = 5;
        ProductEntity p = product(productId, initialStock);

        when(productRepo.findById(productId)).thenReturn(Optional.of(p));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> ReflectionTestUtils.invokeMethod(cartService, "updateProductStock", productId, deduct)
        );
        assertTrue(ex.getMessage().toLowerCase().contains("insufficient"));

        assertEquals(initialStock, p.getStock());
        verify(productRepo).findById(productId);
        verify(productRepo, never()).save(any());
    }

    @Test
    @DisplayName("update stock - throws EntityNotFoundException when product id not found")
    void updateProductStock_notFound() {
        Long productId = 99L;

        when(productRepo.findById(productId)).thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> ReflectionTestUtils.invokeMethod(cartService, "updateProductStock", productId, 1)
        );
        verify(productRepo).findById(productId);
        verify(productRepo, never()).save(any());
    }
}
