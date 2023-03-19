package com.yildiz.serhat.coffeestoreservice.service.impl;

import com.yildiz.serhat.coffeestoreservice.repository.OrderItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class OrderItemServiceImplTest {

    @InjectMocks
    private OrderItemServiceImpl orderItemService;
    @Mock
    private OrderItemRepository repository;

    @Test
    public void shouldGetAllItems() {
        orderItemService.getAllOrderItems();
        verify(repository, atLeastOnce()).findAll();
    }
}