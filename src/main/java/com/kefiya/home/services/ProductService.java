package com.kefiya.home.services;

import com.kefiya.home.database.entities.ProductEntity;
import com.kefiya.home.database.repos.ProductRepo;
import com.kefiya.home.models.ProductCreateModel;
import com.kefiya.home.models.ProductOutModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepo productRepo;


    public ProductOutModel create(ProductCreateModel createModel) throws IllegalArgumentException {

        var entity = ProductCreateModel.buildEntity(createModel);
        entity = productRepo.save(entity);
        return ProductOutModel.buildOut(saveOrUpdate(entity));
    }


    public ProductOutModel detail(Long id) throws EntityNotFoundException {
        var product = productRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Product is not found")
        );

        return ProductOutModel.buildOut(product);
    }

    /**
        simple paginated result
    * */
    public Page<ProductOutModel> listAll(Integer pageNumber) throws EntityNotFoundException {
        var pageRequest = PageRequest.of(pageNumber, 10);
        var products = productRepo.findAll(pageRequest);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("Products are not found");
        }

        List<ProductOutModel> productOutModels = products
                .stream()
                .map(ProductOutModel::buildOut)
                .toList();

        return new PageImpl<>(productOutModels, pageRequest, products.getTotalElements());
    }

    private ProductEntity saveOrUpdate(ProductEntity product) {
        try {
            product = productRepo.save(product);
            log.info("Product id :{} update/created successfully", product.getId());
            return product;

        } catch (DataIntegrityViolationException ex) {
            log.error("Failed to create/update product: {}", ex.getMessage());
            throw new IllegalArgumentException("violation of data integration. Refer manual");   //limiting exception details to client
        }
    }
}
