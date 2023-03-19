package com.yildiz.serhat.coffeestoreservice.repository;

import com.yildiz.serhat.coffeestoreservice.domain.entity.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {
}
