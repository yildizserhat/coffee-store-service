package com.yildiz.serhat.coffeestoreservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@Entity
@Table(name = "order_item")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "quantity")
    private int quantity;

    @NotNull
    @Column(name = "drink")
    private String drink;

    @NotNull
    @Column(name = "price")
    private BigDecimal price;

    @NotNull
    @Column(name = "toppings")
    private String toppings;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;


    public static OrderItem buildOrderItem(BasketItem basketItem) {
        String drink = basketItem.getProducts().stream().filter(ProductType::isDrink)
                .findFirst()
                .map(Product::getName).toString();
        String topping = basketItem.getProducts().stream().filter(y -> !ProductType.isDrink(y))
                .map(Product::getName).collect(Collectors.joining(","));
        return OrderItem.builder()
                .price(basketItem.getAmount())
                .quantity(basketItem.getQuantity())
                .drink(drink)
                .toppings(topping).build();

    }
}
