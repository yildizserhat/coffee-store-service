package com.yildiz.serhat.coffeestoreservice.service;

import com.yildiz.serhat.coffeestoreservice.controller.model.request.CreateItemRequestDTO;
import com.yildiz.serhat.coffeestoreservice.domain.entity.Basket;

public interface BasketService {

    Basket createItemInBasket(CreateItemRequestDTO requestDTO, String token);

    Basket removeItemFromBasket(Long id, int quantity, String token);

    Basket getAllItems(String token);

    void clearBasket(String token);

    Basket getBasket(String token);

    void calculateBasketDiscount(Basket basket);
}
