package com.yildiz.serhat.coffeestoreservice.service;

import com.yildiz.serhat.coffeestoreservice.domain.entity.User;

public interface UserService {

    void register(User user);

    String getUserToken(String email);

    User getUserByToken(String token);
}
