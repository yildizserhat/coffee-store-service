package com.yildiz.serhat.coffeestoreservice.controller;

import com.yildiz.serhat.coffeestoreservice.domain.entity.Order;
import com.yildiz.serhat.coffeestoreservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/v1/api/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestHeader(name = "Authorization") String token) {
        return new ResponseEntity<>(orderService.placeOrder(token), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(@RequestHeader(name = "Authorization") String token) {
        return new ResponseEntity<>(orderService.getAllOrders(token), HttpStatus.OK);
    }
}
