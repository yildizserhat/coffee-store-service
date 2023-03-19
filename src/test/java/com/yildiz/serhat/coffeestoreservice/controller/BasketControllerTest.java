package com.yildiz.serhat.coffeestoreservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yildiz.serhat.coffeestoreservice.controller.model.request.CreateItemRequestDTO;
import com.yildiz.serhat.coffeestoreservice.controller.model.response.ResponseDTO;
import com.yildiz.serhat.coffeestoreservice.domain.entity.User;
import com.yildiz.serhat.coffeestoreservice.repository.UserRepository;
import com.yildiz.serhat.coffeestoreservice.service.BasketService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
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
class BasketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private BasketService basketService;

    private CreateItemRequestDTO createItemRequestDTO;

    @Autowired
    private LoginController loginController;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    public void setUp() {
        objectMapper.setVisibility(FIELD, ANY);
        createItemRequestDTO = new CreateItemRequestDTO(1, Set.of(1L, 2L), 1L);
    }

    @AfterTestClass
    public void tearDown(){
        userRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    public void shouldAddItemInBasket() {
        String token = getToken("createTest@test.com");
        mockMvc.perform(post("/v1/api/basket/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(createItemRequestDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    public void shouldRemoveItemFromBasket() {
        String token = getToken("remove@test.com");
        mockMvc.perform(delete("/v1/api/basket/items/{id}/{quantity}", 1L, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    public void shouldGetAllItems() {
        String token = getToken("getAllItems@test.com");
        mockMvc.perform(get("/v1/api/basket/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    public void shouldClearBasket() {
        String token = getToken("clearBasket@test.com");
        mockMvc.perform(delete("/v1/api/basket/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    private String getToken(String email) {
        ResponseEntity<?> responseEntity = loginController
                .registerCustomer(User.builder().email(email).build());
        ResponseEntity<?> login = loginController.login(email);
       ResponseDTO responseDTO= (ResponseDTO) login.getBody();
        return responseDTO.getObject().toString();
    }
}