package com.yildiz.serhat.coffeestoreservice.service.impl;

import com.yildiz.serhat.coffeestoreservice.domain.entity.OrderItem;
import com.yildiz.serhat.coffeestoreservice.repository.OrderItemRepository;
import com.yildiz.serhat.coffeestoreservice.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository repository;


    @Override
    public List<OrderItem> getAllOrderItems() {
        return repository.findAll();
    }
}
