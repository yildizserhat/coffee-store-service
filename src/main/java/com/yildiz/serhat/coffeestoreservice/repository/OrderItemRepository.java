package com.yildiz.serhat.coffeestoreservice.repository;

import com.yildiz.serhat.coffeestoreservice.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
