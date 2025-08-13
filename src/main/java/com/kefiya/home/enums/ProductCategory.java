package com.kefiya.home.enums;

public enum ProductCategory {
    COFFEE("Coffee"),
    CEREALS("Cereals"),
    COSTMETICS("Cosmetics"),
    ELECTRONICS("Electronics"),
    HOME_APPLIANCE("Home Appliances");

    String title;

    ProductCategory(String title){
        this.title = title;
    }
}
