package com.yildiz.serhat.coffeestoreservice.service.impl;

import com.yildiz.serhat.coffeestoreservice.domain.entity.User;
import com.yildiz.serhat.coffeestoreservice.exception.UserAlreadyExistsException;
import com.yildiz.serhat.coffeestoreservice.exception.UserNotFoundException;
import com.yildiz.serhat.coffeestoreservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @Test
    public void shouldNotRegisterUserIfUserExist() {
        String email = "serhat@yildiz.com";

        User user = User.builder().email(email).id(1L).build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistsException.class,
                () -> userService.register(user));
    }

    @Test
    public void shouldRegisterUser() {
        String email = "serhat@yildiz.com";

        User user = User.builder().email(email).id(1L).build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        userService.register(user);

        verify(userRepository, atLeastOnce()).save(user);
    }

    @Test
    public void shouldThrowExceptionWhenGetNotExistUserByEmail() {
        String email = "serhat@yildiz.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.getUserToken(email));
    }

    @Test
    public void shouldGetUserByEmail() {
        String email = "serhat@yildiz.com";

        User user = User.builder().email(email).token("token").id(1L).build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        String userToken = userService.getUserToken(email);

        assertEquals(userToken, "token");
    }

    @Test
    public void shouldThrowExceptionWhenGetNotExistUserByToken() {
        String token = "token";

        when(userRepository.findByToken(token)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.getUserByToken(token));
    }

    @Test
    public void shouldGetUserByToken() {
        String token = "token";
        User user = User.builder().token(token).id(1L).build();

        when(userRepository.findByToken(token)).thenReturn(Optional.of(user));

        User userByToken = userService.getUserByToken(token);

        assertEquals(userByToken.getToken(), token);
    }
}