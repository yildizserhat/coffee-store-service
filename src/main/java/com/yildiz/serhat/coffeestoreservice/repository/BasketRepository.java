package com.yildiz.serhat.coffeestoreservice.repository;

import com.yildiz.serhat.coffeestoreservice.domain.entity.Basket;
import com.yildiz.serhat.coffeestoreservice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {

    Optional<Basket> findByUser(User user);
}
