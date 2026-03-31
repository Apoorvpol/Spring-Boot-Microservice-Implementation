package com.example.product_service.service;

import com.example.product_service.Dto.ProductRequest;
import com.example.product_service.Dto.ProductResponse;

import com.example.product_service.model.Product;
import com.example.product_service.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Builder
public class ProductService {

    private final ProductRepository productRepository;


    public Product createProduct(ProductRequest productRequest) {

        Product product = Product.builder()
                .id(productRequest.id())
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();

        productRepository.save(product);
        log.info("Product created succesfully");
        return product;
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice()
                ))
                .toList();
    }
}
