package com.yildiz.serhat.coffeestoreservice.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@Table(name = "basket")
@NoArgsConstructor
@Getter
@Setter
public class Basket extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL)
    private List<BasketItem> basketItems = new ArrayList<>();

    @Transient
    private BigDecimal discountedAmount = BigDecimal.ZERO;

    @OneToOne
    @JsonIgnore
    private User user;

    public void addItemInBasket(BasketItem basketItem) {
        basketItems.stream()
                .filter(basketItem::equals)
                .findAny()
                .ifPresentOrElse(item -> item.setQuantity(item.getQuantity() + basketItem.getQuantity()),
                        () -> this.basketItems.add(basketItem));
    }

    public void removeItemFromBasket(BasketItem basketItem, int quantity) {
        this.basketItems.stream()
                .filter(basketItem::equals)
                .findAny()
                .ifPresent(item -> {
                    if (quantity >= item.getQuantity()) {
                        this.basketItems.remove(item);
                    } else {
                        item.setQuantity(Math.max(item.getQuantity() - quantity, 0));
                    }
                });
    }

    public BigDecimal getTotalAmount() {
        return basketItems.stream()
                .map(BasketItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Basket basket = (Basket) o;
        return getId() != null && Objects.equals(getId(), basket.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
