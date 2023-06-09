package com.yildiz.serhat.coffeestoreservice.controller;

import com.yildiz.serhat.coffeestoreservice.controller.model.request.ProductCreateRequestDTO;
import com.yildiz.serhat.coffeestoreservice.controller.model.request.ProductUpdateRequestDTO;
import com.yildiz.serhat.coffeestoreservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RequestMapping("/v1/api/admin/products")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final ProductService productService;

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductCreateRequestDTO request) {
        productService.createProduct(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequestDTO request) {
        productService.updateProduct(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/most-used-toppings")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Integer>> getMostUsedProducts() {
        return ResponseEntity.ok(productService.getMostUsedProducts());
    }
}
