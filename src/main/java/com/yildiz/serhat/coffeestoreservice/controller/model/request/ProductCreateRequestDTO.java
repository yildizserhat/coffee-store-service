package com.yildiz.serhat.coffeestoreservice.controller.model.request;

import com.yildiz.serhat.coffeestoreservice.domain.entity.ProductType;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


public record ProductCreateRequestDTO(
        @NotNull(message = "Product Name cannot be empty.")
        String name,
        @NotNull(message = "Product Type cannot be empty.")
        @Enumerated(EnumType.STRING)
        ProductType type,
        @DecimalMin(value = "0.0", message = "Product Price must be greater than 0.")
        BigDecimal price
) { }
