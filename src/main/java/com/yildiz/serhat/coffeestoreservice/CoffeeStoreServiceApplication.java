package com.yildiz.serhat.coffeestoreservice;

import com.yildiz.serhat.coffeestoreservice.domain.entity.Product;
import com.yildiz.serhat.coffeestoreservice.domain.entity.ProductType;
import com.yildiz.serhat.coffeestoreservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class CoffeeStoreServiceApplication {

    private final ProductRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(CoffeeStoreServiceApplication.class, args);
    }

    @PostConstruct
    public void setup() {
        Product drink1 = new Product("Black Coffee", ProductType.DRINK, BigDecimal.valueOf(4), true);
        Product drink2 = new Product("Latte", ProductType.DRINK, BigDecimal.valueOf(5), true);
        Product drink3 = new Product("Mocha", ProductType.DRINK, BigDecimal.valueOf(6), true);
        Product drink4 = new Product("Tea", ProductType.DRINK, BigDecimal.valueOf(3), true);


        Product topping1 = new Product("Milk", ProductType.TOPPING, BigDecimal.valueOf(2), true);
        Product topping2 = new Product("Hazelnut Syrup", ProductType.TOPPING, BigDecimal.valueOf(3), true);
        Product topping3 = new Product("Chocolate Sauce", ProductType.TOPPING, BigDecimal.valueOf(5), true);
        Product topping4 = new Product("Lemon", ProductType.TOPPING, BigDecimal.valueOf(2), true);
        repository.saveAll(List.of(drink1, drink2, drink3, drink4, topping1, topping2, topping3, topping4));
    }

}
