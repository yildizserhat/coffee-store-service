package com.yildiz.serhat.coffeestoreservice.repository;

import com.yildiz.serhat.coffeestoreservice.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByEmail(String email);
}
