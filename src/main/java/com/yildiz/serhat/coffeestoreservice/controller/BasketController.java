package com.yildiz.serhat.coffeestoreservice.controller;

import com.yildiz.serhat.coffeestoreservice.controller.model.request.CreateItemRequestDTO;
import com.yildiz.serhat.coffeestoreservice.domain.entity.Basket;
import com.yildiz.serhat.coffeestoreservice.service.BasketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/api/basket")
public class BasketController {

    private final BasketService basketService;

    @PostMapping("/items")
    public ResponseEntity<Basket> addItem(@Valid @RequestBody CreateItemRequestDTO requestDTO,
                                          @RequestHeader(name = "Authorization") String token) {
        return new ResponseEntity<>(basketService.createItemInBasket(requestDTO, token), HttpStatus.CREATED);
    }

    @DeleteMapping("/items/{id}/{quantity}")
    public ResponseEntity<Basket> removeItem(@PathVariable Long id,
                                             @PathVariable int quantity,
                                             @RequestHeader(name = "Authorization") String token) {
        return new ResponseEntity<>(basketService.removeItemFromBasket(id, quantity, token), HttpStatus.OK);
    }

    @GetMapping("/items")
    public ResponseEntity<Basket> getAllItems(@RequestHeader(name = "Authorization") String token) {
        return new ResponseEntity<>(basketService.getAllItems(token), HttpStatus.OK);
    }

    @DeleteMapping("/items")
    public ResponseEntity<Basket> clearBasket(@RequestHeader(name = "Authorization") String token) {
        basketService.clearBasket(token);
        return ResponseEntity.ok().build();
    }
}
