package com.yildiz.serhat.coffeestoreservice.controller.model.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;


public record CreateItemRequestDTO(
        @NotNull
        @Min(value = 1, message = "Please enter Valid number.")
        Integer quantity,
        Set<Long> toppings,
        @NotNull
        Long drinkId
) {
}
