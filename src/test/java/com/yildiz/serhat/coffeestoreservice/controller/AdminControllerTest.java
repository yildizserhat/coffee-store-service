package com.yildiz.serhat.coffeestoreservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yildiz.serhat.coffeestoreservice.controller.model.request.ProductCreateRequestDTO;
import com.yildiz.serhat.coffeestoreservice.controller.model.request.ProductUpdateRequestDTO;
import com.yildiz.serhat.coffeestoreservice.controller.model.response.ResponseDTO;
import com.yildiz.serhat.coffeestoreservice.domain.entity.Product;
import com.yildiz.serhat.coffeestoreservice.domain.entity.ProductType;
import com.yildiz.serhat.coffeestoreservice.domain.entity.User;
import com.yildiz.serhat.coffeestoreservice.repository.ProductRepository;
import com.yildiz.serhat.coffeestoreservice.repository.UserRepository;
import com.yildiz.serhat.coffeestoreservice.service.ProductService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductService productService;

    private ProductCreateRequestDTO productCreateRequestDTO;

    private ProductUpdateRequestDTO productUpdateRequestDTO;

    private Map<String, Integer> mostUsedProducts;

    @Autowired
    private LoginController loginController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository repository;

    @BeforeEach
    public void setUp() {
        objectMapper.setVisibility(FIELD, ANY);
        productCreateRequestDTO = new ProductCreateRequestDTO("Create", ProductType.DRINK, BigDecimal.TEN);
        productUpdateRequestDTO = new ProductUpdateRequestDTO("Update", ProductType.TOPPING, BigDecimal.ONE);
        mostUsedProducts = Collections.singletonMap("Test Product", 5);
    }

    @AfterTestClass
    public void tearDown(){
        userRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    public void shouldCreateProductAndReturnsCreated() {
        String token = getToken("createTest@test.com");
        mockMvc.perform(post("/v1/api/admin/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(productCreateRequestDTO)))
                .andExpect(status().isCreated());

        List<Product> all = repository.findAll();
        Product product = all.get(7);

        assertEquals(product.getType(), productCreateRequestDTO.type());
        assertEquals(product.getName(), productCreateRequestDTO.name());
        assertEquals(product.getUnitPrice().setScale(2), productCreateRequestDTO.price().setScale(2));
    }


    @Test
    @SneakyThrows
    public void shouldUpdateProductAndReturnsOk() {
        Long id = 3L;
        String token = getToken("updateTest@test.com");

        mockMvc.perform(put("/v1/api/admin/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(productUpdateRequestDTO)))
                .andExpect(status().isOk());

        List<Product> all = repository.findAll();
        Product product = all.get(1);

        assertEquals(product.getType(), productUpdateRequestDTO.type());
        assertEquals(product.getName(), productUpdateRequestDTO.name());
        assertEquals(product.getUnitPrice().setScale(2), productUpdateRequestDTO.price().setScale(2));
    }

    @Test
    @SneakyThrows
    public void shouldDeleteProductAndReturnsOk() {
        Long id = 1L;
        String token = getToken("deleteTest@test.com");
        mockMvc.perform(delete("/v1/api/admin/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        List<Product> all = repository.findAll();
        Product product = all.get(0);

        assertEquals(all.size(), 7);
    }


    private String getToken(String email) {
        ResponseEntity<?> responseEntity = loginController
                .registerCustomer(User.builder().email(email).build());
        ResponseEntity<?> login = loginController.login(email);
        ResponseDTO responseDTO= (ResponseDTO) login.getBody();
        return responseDTO.getObject().toString();
    }
}
