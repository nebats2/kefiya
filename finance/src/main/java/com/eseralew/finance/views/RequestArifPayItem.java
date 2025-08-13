package com.kefiya.home.views;

import com.kefiya.home.models.ItemModel;
import lombok.Data;
import lombok.Getter;

@Data
public class RequestArifPayItem {
    String name;
    Integer quantity;
    Float price;
    String description;

    public static  RequestArifPayItem build(ItemModel model){

        var request = new RequestArifPayItem();
         request.setName(model.getName());
         request.setDescription(model.getDescription());
         request.setPrice(model.getPrice());
         request.setQuantity(model.getQuantity());
         return request;
    }
}
