package com.yildiz.serhat.coffeestoreservice.controller;

import com.yildiz.serhat.coffeestoreservice.controller.model.response.ResponseDTO;
import com.yildiz.serhat.coffeestoreservice.domain.entity.User;
import com.yildiz.serhat.coffeestoreservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/authenticate")
public class LoginController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody User user) {
        userService.register(user);
        return ResponseEntity.ok(ResponseDTO.builder().success(true).message("User Registered.")
                .object(user.getEmail())
                .build());
    }

    @PostMapping("/login")
    public ResponseDTO<Object> login(@RequestParam("email") String email) {
        return ResponseDTO.builder()
                .object(userService.getUserToken(email))
                .success(true)
                .build();
    }
}
