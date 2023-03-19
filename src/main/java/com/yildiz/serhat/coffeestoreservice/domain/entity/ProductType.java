package com.yildiz.serhat.coffeestoreservice.domain.entity;

public enum ProductType {
    DRINK, TOPPING;


    public static boolean isDrink(Product product) {
        return DRINK.equals(product.getType());
    }
}
