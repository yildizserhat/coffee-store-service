package com.yildiz.serhat.coffeestoreservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yildiz.serhat.coffeestoreservice.controller.model.request.CreateItemRequestDTO;
import com.yildiz.serhat.coffeestoreservice.controller.model.response.ResponseDTO;
import com.yildiz.serhat.coffeestoreservice.domain.entity.User;
import com.yildiz.serhat.coffeestoreservice.repository.UserRepository;
import com.yildiz.serhat.coffeestoreservice.service.OrderService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private OrderService orderService;

    @Autowired
    private LoginController loginController;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    public void setUp() {
        objectMapper.setVisibility(FIELD, ANY);
    }

    @AfterTestClass
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    public void shouldPlaceOrder() {
        String token = getToken("placeOrder@test.com");
        mockMvc.perform(post("/v1/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    public void shouldGetAllOrders() {
        String token = getToken("remove@test.com");
        mockMvc.perform(get("/v1/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }


    private String getToken(String email) {
        ResponseEntity<?> responseEntity = loginController
                .registerCustomer(User.builder().email(email).build());
        ResponseEntity<?> login = loginController.login(email);
        ResponseDTO responseDTO = (ResponseDTO) login.getBody();
        return responseDTO.getObject().toString();
    }

}