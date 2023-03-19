package com.yildiz.serhat.coffeestoreservice.service.impl;

import com.yildiz.serhat.coffeestoreservice.controller.model.request.CreateItemRequestDTO;
import com.yildiz.serhat.coffeestoreservice.domain.entity.Basket;
import com.yildiz.serhat.coffeestoreservice.domain.entity.BasketItem;
import com.yildiz.serhat.coffeestoreservice.domain.entity.Product;
import com.yildiz.serhat.coffeestoreservice.domain.entity.User;
import com.yildiz.serhat.coffeestoreservice.repository.BasketRepository;
import com.yildiz.serhat.coffeestoreservice.service.ProductService;
import com.yildiz.serhat.coffeestoreservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasketServiceImplTest {

    @InjectMocks
    private BasketServiceImpl basketService;
    @Mock
    private UserService userService;
    @Mock
    private ProductService productService;
    @Mock
    private BasketRepository basketRepository;

    @Test
    public void shouldCreateItemInBasket() {
        String token = "token";
        CreateItemRequestDTO createItemRequestDTO = new CreateItemRequestDTO(1, Set.of(1L, 2L), 3L);
        User user1 = User.builder().id(1L).build();
        Product product1 = Product.builder().unitPrice(BigDecimal.ONE).build();
        Product product2 = Product.builder().unitPrice(BigDecimal.ONE).build();
        Product product3 = Product.builder().unitPrice(BigDecimal.ONE).build();
        ArrayList<BasketItem> basketItems = new ArrayList<>();
        BasketItem basketItem = new BasketItem();
        basketItems.add(basketItem);
        Basket basket = Basket.builder().user(user1).basketItems(basketItems).build();


        when(userService.getUserByToken(token)).thenReturn(user1);
        when(productService.getProduct(1L)).thenReturn(product1);
        when(productService.getProduct(2L)).thenReturn(product2);
        when(productService.getProduct(3L)).thenReturn(product3);
        when(basketRepository.findByUser(user1)).thenReturn(Optional.ofNullable(basket));
        when(basketRepository.save(basket)).thenReturn(basket);

        Basket itemInBasket = basketService.createItemInBasket(createItemRequestDTO, token);

        verify(basketRepository, atLeastOnce()).save(any());

        assertEquals(itemInBasket.getUser(), user1);
        assertEquals(itemInBasket.getBasketItems(), basketItems);

    }

    @Test
    public void shouldRemoveFromBasket() {
        String token = "token";

        User user1 = User.builder().id(1L).build();

        ArrayList<BasketItem> basketItems = new ArrayList<>();
        BasketItem basketItem = new BasketItem();
        basketItem.setId(1L);
        basketItems.add(basketItem);
        Basket basket = Basket.builder().user(user1).basketItems(basketItems).build();


        when(userService.getUserByToken(token)).thenReturn(user1);
        when(basketRepository.findByUser(user1)).thenReturn(Optional.ofNullable(basket));

        Basket basket1 = basketService.removeItemFromBasket(1L, 1, token);

        assertEquals(basket1.getUser(), user1);
        assertTrue(basket1.getBasketItems().isEmpty());
    }


    @Test
    public void shouldCalculateDiscount() {
        User user1 = User.builder().id(1L).build();

        ArrayList<BasketItem> basketItems = new ArrayList<>();
        BasketItem e = new BasketItem();
        e.setId(1L);
        basketItems.add(e);
        Basket basket = Basket.builder().user(user1).basketItems(basketItems).build();

        basketService.calculateBasketDiscount(basket);

        assertNotNull(basket.getDiscountedAmount());
    }
}