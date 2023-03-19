package com.yildiz.serhat.coffeestoreservice.service.impl;

import com.yildiz.serhat.coffeestoreservice.domain.entity.User;
import com.yildiz.serhat.coffeestoreservice.exception.UserAlreadyExistsException;
import com.yildiz.serhat.coffeestoreservice.exception.UserNotFoundException;
import com.yildiz.serhat.coffeestoreservice.repository.UserRepository;
import com.yildiz.serhat.coffeestoreservice.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void register(User user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(u -> {
            throw new UserAlreadyExistsException(u.getUsername());
        });
        user.setToken(getJTWToken(user.getUsername()));
        userRepository.save(user);
        log.info("User successfully created:{} ", user.getUsername());
    }

    @Override
    public String getUserToken(String email) {
        return userRepository.findByEmail(email).map(User::getToken)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }

    @Override
    public User getUserByToken(String token) {
        return userRepository.findByToken(prepareToken(token))
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }

    private String getJTWToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("coffeeStoreAppJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return token;
    }

    private String prepareToken(String token) {
        return token.replace("Bearer ", "").strip();
    }
}





