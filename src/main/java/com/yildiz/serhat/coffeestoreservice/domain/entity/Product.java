package com.yildiz.serhat.coffeestoreservice.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yildiz.serhat.coffeestoreservice.controller.model.request.ProductCreateRequestDTO;
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
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@Table(name = "products")
@NoArgsConstructor
@Getter
@Setter
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private ProductType type;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "active")
    private boolean active;

    @JsonIgnore
    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private List<BasketItem> basketItemList;

    public Product(String name, ProductType type, BigDecimal unitPrice, boolean active) {
        this.name = name;
        this.type = type;
        this.unitPrice = unitPrice;
        this.active = active;
    }

    public static Product buildProductFromCreateRequest(ProductCreateRequestDTO requestDTO) {
        return Product.builder()
                .name(requestDTO.name())
                .type(requestDTO.type())
                .unitPrice(requestDTO.price())
                .active(true)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return getId() != null && Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
