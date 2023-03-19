package com.yildiz.serhat.coffeestoreservice.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "email")
    private String email;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "discounted_amount")
    private BigDecimal discountedAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderItem> orderItemList = new ArrayList<>();

    public static Order buildOrderFromOrderItems(List<OrderItem> orderItems, Basket basket) {
        return Order.builder()
                .orderItemList(orderItems)
                .status(OrderStatus.IN_PROGRESS)
                .totalAmount(basket.getTotalAmount())
                .discountedAmount(basket.getDiscountedAmount())
                .email(basket.getUser().getEmail()).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return getId() != null && Objects.equals(getId(), order.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
