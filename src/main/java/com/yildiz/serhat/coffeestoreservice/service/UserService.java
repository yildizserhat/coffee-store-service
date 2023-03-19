package com.yildiz.serhat.coffeestoreservice.service;

import com.yildiz.serhat.coffeestoreservice.domain.entity.User;

import java.util.Optional;

public interface UserService {

    void register(User user);

    String getUserToken(String email);

    User getUserByToken(String token);
}
