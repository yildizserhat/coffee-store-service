package com.yildiz.serhat.coffeestoreservice.repository;

import com.yildiz.serhat.coffeestoreservice.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
