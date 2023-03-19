package com.yildiz.serhat.coffeestoreservice.service.impl;

import com.yildiz.serhat.coffeestoreservice.domain.entity.Basket;
import com.yildiz.serhat.coffeestoreservice.domain.entity.Order;
import com.yildiz.serhat.coffeestoreservice.domain.entity.OrderItem;
import com.yildiz.serhat.coffeestoreservice.domain.entity.User;
import com.yildiz.serhat.coffeestoreservice.repository.OrderRepository;
import com.yildiz.serhat.coffeestoreservice.service.BasketService;
import com.yildiz.serhat.coffeestoreservice.service.OrderService;
import com.yildiz.serhat.coffeestoreservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final UserService userService;

    private final BasketService basketService;

    @Override
    @Transactional
    public Order placeOrder(String token) {
        Basket basket = getBasket(token);
        List<OrderItem> orderItems = basket.getBasketItems()
                .stream()
                .map(OrderItem::buildOrderItem)
                .toList();

        Order order = Order.buildOrderFromOrderItems(orderItems, basket);
        Order savedOrder = orderRepository.save(order);
        basketService.clearBasket(token);
        log.info("Basket got empty for userId: {}", basket.getUser().getId());
        log.info("Order successfully created for UserID: {} withOrderNumber: {}",
                basket.getUser().getId(), savedOrder.getId());
        return savedOrder;
    }

    private Basket getBasket(String token) {
        Basket basket = basketService.getBasket(token);
        basketService.calculateBasketDiscount(basket);
        return basket;
    }

    @Override
    public List<Order> getAllOrders(String token) {
        User userByToken = userService.getUserByToken(token);
        return orderRepository.findByEmail(userByToken.getEmail());
    }
}
