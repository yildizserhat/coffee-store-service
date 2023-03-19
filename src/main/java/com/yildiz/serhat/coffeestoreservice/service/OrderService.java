package com.yildiz.serhat.coffeestoreservice.service;

import com.yildiz.serhat.coffeestoreservice.domain.entity.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(String token);

    List<Order> getAllOrders(String token);
}
