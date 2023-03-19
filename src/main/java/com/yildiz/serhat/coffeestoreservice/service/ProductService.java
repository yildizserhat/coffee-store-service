package com.yildiz.serhat.coffeestoreservice.service;

import com.yildiz.serhat.coffeestoreservice.controller.model.request.ProductCreateRequestDTO;
import com.yildiz.serhat.coffeestoreservice.controller.model.request.ProductUpdateRequestDTO;
import com.yildiz.serhat.coffeestoreservice.controller.model.response.ReportResponseDTO;
import com.yildiz.serhat.coffeestoreservice.domain.entity.Product;

import java.util.Map;

public interface ProductService {

    void createProduct(ProductCreateRequestDTO requestDTO);

    void updateProduct(Long id, ProductUpdateRequestDTO requestDTO);

    void deleteProduct(Long id);

    Map<String, Integer> getMostUsedProducts();

    Product getProduct(Long id);
}
