package com.yildiz.serhat.coffeestoreservice.service.impl;

import com.yildiz.serhat.coffeestoreservice.controller.model.request.ProductCreateRequestDTO;
import com.yildiz.serhat.coffeestoreservice.controller.model.request.ProductUpdateRequestDTO;
import com.yildiz.serhat.coffeestoreservice.domain.entity.OrderItem;
import com.yildiz.serhat.coffeestoreservice.domain.entity.Product;
import com.yildiz.serhat.coffeestoreservice.exception.ProductNotFoundException;
import com.yildiz.serhat.coffeestoreservice.repository.ProductRepository;
import com.yildiz.serhat.coffeestoreservice.service.OrderItemService;
import com.yildiz.serhat.coffeestoreservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    private final OrderItemService orderItemService;


    @Override
    public void createProduct(ProductCreateRequestDTO requestDTO) {
        Product product = repository.save(Product.buildProductFromCreateRequest(requestDTO));
        log.info("{} product saved with ID: {}", product.getName(), product.getId());
    }

    @Override
    public void updateProduct(Long id, ProductUpdateRequestDTO requestDTO) {
        Optional<Product> productOptional = repository.findById(id);
        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException(String.format("%s product not found ", id));
        }
        Product product = productOptional.get();
        product.setName(requestDTO.name());
        product.setType(requestDTO.type());
        product.setUnitPrice(requestDTO.price());
        repository.save(product);
        log.info("{} product updated with ID: {}", product.getName(), product.getId());
    }

    @Override
    public void deleteProduct(Long id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException(String.format("%s product not found ", id));
        }
        repository.deleteById(id);
        log.info("Product deleted with ID: {}", id);

    }

    @Override
    public Map<String, Integer> getMostUsedProducts() {
        List<OrderItem> allOrderItems = orderItemService.getAllOrderItems();
        Map<String, Integer> mostUsedToppings = new HashMap<>();
        allOrderItems.forEach(y -> {
            Arrays.stream(y.getToppings().split(",")).forEach(f -> {
                mostUsedToppings.put(f, mostUsedToppings.getOrDefault(f, 0) + y.getQuantity());
            });
        });
        return mostUsedToppings;
    }

    @Override
    public Product getProduct(Long id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
    }
}
