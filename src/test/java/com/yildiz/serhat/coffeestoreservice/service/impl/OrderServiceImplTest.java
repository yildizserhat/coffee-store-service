package com.yildiz.serhat.coffeestoreservice.service.impl;

import com.yildiz.serhat.coffeestoreservice.domain.entity.Basket;
import com.yildiz.serhat.coffeestoreservice.domain.entity.BasketItem;
import com.yildiz.serhat.coffeestoreservice.domain.entity.Order;
import com.yildiz.serhat.coffeestoreservice.domain.entity.OrderStatus;
import com.yildiz.serhat.coffeestoreservice.domain.entity.User;
import com.yildiz.serhat.coffeestoreservice.repository.OrderRepository;
import com.yildiz.serhat.coffeestoreservice.service.BasketService;
import com.yildiz.serhat.coffeestoreservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserService userService;
    @Mock
    private BasketService basketService;

    @Test
    public void shouldPlaceOrder() {
        String token = "token";
        User user1 = User.builder().id(1L).build();

        ArrayList<BasketItem> basketItems = new ArrayList<>();
        BasketItem basketItem = new BasketItem();
        basketItem.setId(1L);
        basketItems.add(basketItem);
        Basket basket = Basket.builder().user(user1).basketItems(basketItems).build();
        String email = "serhat@yildiz.com";
        Order order = Order.builder().email(email).status(OrderStatus.IN_PROGRESS).build();
        when(basketService.getBasket(token)).thenReturn(basket);
        when(orderRepository.save(any())).thenReturn(order);

        Order returnedOrder = orderService.placeOrder(token);

        verify(basketService, atLeastOnce()).clearBasket(token);
        assertEquals(returnedOrder.getStatus(), OrderStatus.IN_PROGRESS);
        assertEquals(returnedOrder.getEmail(), email);
    }

    @Test
    public void shouldGetAllOrders() {
        String token = "token";
        String email = "serhat@yildiz.com";

        User user1 = User.builder().email(email).id(1L).build();

        when(userService.getUserByToken(token)).thenReturn(user1);

        orderService.getAllOrders(token);

        verify(orderRepository, atLeastOnce()).findByEmail(email);
    }

}