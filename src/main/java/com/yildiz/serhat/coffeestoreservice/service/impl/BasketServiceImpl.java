package com.yildiz.serhat.coffeestoreservice.service.impl;

import com.yildiz.serhat.coffeestoreservice.controller.model.request.CreateItemRequestDTO;
import com.yildiz.serhat.coffeestoreservice.domain.entity.Basket;
import com.yildiz.serhat.coffeestoreservice.domain.entity.BasketItem;
import com.yildiz.serhat.coffeestoreservice.domain.entity.Product;
import com.yildiz.serhat.coffeestoreservice.domain.entity.User;
import com.yildiz.serhat.coffeestoreservice.repository.BasketRepository;
import com.yildiz.serhat.coffeestoreservice.service.BasketService;
import com.yildiz.serhat.coffeestoreservice.service.ProductService;
import com.yildiz.serhat.coffeestoreservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final UserService userService;

    private final ProductService productService;

    private final BasketRepository basketRepository;

    @Override
    @Transactional
    public Basket createItemInBasket(CreateItemRequestDTO itemRequestDTO, String token) {
        Basket basket = getBasket(token);

        List<Product> products = new ArrayList<>();
        BasketItem basketItem = buildBasketItem(itemRequestDTO, basket, products);

        basket.addItemInBasket(basketItem);
        Basket savedBasket = basketRepository.save(basket);
        calculateBasketDiscount(savedBasket);

        log.info("Basket saved for userId:{}, userEmail: {}", savedBasket.getUser().getId(), savedBasket.getUser().getEmail());
        return savedBasket;
    }

    @Override
    public Basket removeItemFromBasket(Long id, int quantity, String token) {
        String preparedToken = prepareToken(token);
        Basket basket = getBasket(preparedToken);

        basket.getBasketItems().stream()
                .filter(basketItem -> basketItem.getId() == id)
                .findAny()
                .ifPresent(item -> {
                    basket.removeItemFromBasket(item, quantity);
                });
        calculateBasketDiscount(basket);
        return basket;
    }

    @Override
    public Basket getAllItems(String token) {
        Basket basket = getBasket(token);
        calculateBasketDiscount(basket);
        return basket;
    }

    @Override
    public void clearBasket(String token) {
        Basket basket = getBasket(token);
        basketRepository.delete(basket);
    }

    private BasketItem buildBasketItem(CreateItemRequestDTO itemRequestDTO, Basket basket, List<Product> products) {
        Product product = productService.getProduct(itemRequestDTO.drinkId());
        products.add(product);

        itemRequestDTO.toppings().stream()
                .map(productService::getProduct)
                .forEach(products::add);

        return BasketItem.builder()
                .quantity(itemRequestDTO.quantity())
                .products(products)
                .basket(basket).build();
    }

    @Override
    public Basket getBasket(String token) {
        String prepareToken = prepareToken(token);
        User user = userService.getUserByToken(prepareToken);

        return basketRepository.findByUser(user).orElseGet(() -> {
            Basket basket = new Basket();
            basket.setUser(user);
            return basketRepository.save(basket);
        });
    }

    public void calculateBasketDiscount(Basket basket) {
        BigDecimal firstRuleAmount = BigDecimal.ZERO;
        BigDecimal secondRuleAmount = BigDecimal.ZERO;
        BigDecimal discountedAmount = BigDecimal.ZERO;
        if (basket.getTotalAmount().doubleValue() > 12) {
            firstRuleAmount = basket.getTotalAmount().multiply(new BigDecimal("0.75"));
            discountedAmount = firstRuleAmount;
        }
        if (basket.getBasketItems().stream().mapToInt(BasketItem::getQuantity).sum() >= 3) {
            BigDecimal minAmount = basket.getBasketItems().stream()
                    .map(getLowestPrice())
                    .min(Comparator.naturalOrder())
                    .orElse(BigDecimal.ZERO);
            secondRuleAmount = basket.getTotalAmount().subtract(minAmount);
            discountedAmount = secondRuleAmount;
        }
        if (firstRuleAmount.compareTo(BigDecimal.ZERO) > 0 && secondRuleAmount.compareTo(BigDecimal.ZERO) > 0) {
            discountedAmount = firstRuleAmount.compareTo(secondRuleAmount) > 0 ? secondRuleAmount : firstRuleAmount;
        }

        basket.setDiscountedAmount(discountedAmount);
    }


    private Function<BasketItem, BigDecimal> getLowestPrice() {
        return basketItem -> basketItem.getProducts().stream()
                .map(Product::getUnitPrice)
                .min(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);

    }

    private String prepareToken(String token) {
        return token.replace("Bearer ", "").strip();
    }
}
