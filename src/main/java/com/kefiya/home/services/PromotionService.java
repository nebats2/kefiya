package com.kefiya.home.services;

import com.kefiya.home.database.entities.PromotionEntity;
import com.kefiya.home.database.repos.ProductRepo;
import com.kefiya.home.database.repos.PromotionRepo;
import com.kefiya.home.enums.PromotionTypes;
import com.kefiya.home.models.PromotionCreateModel;
import com.kefiya.home.models.PromotionOutModel;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PromotionService {
    private final PromotionRepo promoRepo;
    private final ProductRepo productRepo;

    public PromotionOutModel create (PromotionCreateModel createModel){
        var entity = new PromotionEntity();
        entity.setType(createModel.getType());

        if (createModel.getType() == PromotionTypes.BUY_X_GET_Y) {
            if (createModel.getBuyXProductId() == null || createModel.getFreeYProductId() == null) {
                throw new IllegalArgumentException("Product x/y can't be empty");
            }
            var prodX =   productRepo.findById(createModel.getBuyXProductId()).orElseThrow(
                    () -> new EntityNotFoundException("Product x is not found")
            );

            var prodY = productRepo.findById(createModel.getFreeYProductId()).orElseThrow(
                    () -> new EntityNotFoundException("Product y is not found")
            );

            entity.setBuyXProduct(prodX);
            entity.setFreeYProduct(prodY);
        }


        entity = entity.build(createModel);
        return PromotionOutModel.buildPromoOut(saveOrUpdate(entity));

    }

    public  PromotionOutModel  get(Long id){
         var promo =  promoRepo.findById(id).orElseThrow(
                 ()->new EntityNotFoundException("promotion is not found")
         );
         return PromotionOutModel.buildPromoOut(promo);
    }

    public List<PromotionOutModel> listAll(){
            var promos = promoRepo.findAll();
            if(promos.isEmpty()){
                throw new EntityNotFoundException("promotions are not found");
            }
            return promos.stream().map(PromotionOutModel::buildPromoOut).toList();
    }

    private PromotionEntity saveOrUpdate(PromotionEntity promo){
        try {
            promo = promoRepo.save(promo);
            log.info("promo id :{} update/created successfully", promo.getId());
            return promo;

        } catch (DataIntegrityViolationException ex) {
            log.error("Failed to create/update promo: {}", ex.getMessage());
            throw new IllegalArgumentException("violation of data integration. Refer manual");   //limiting exception details to client
        }
    }
}
