package com.qeema.order_task.product;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qeema.order_task.exception.ProductNameAlreadyExistsException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ProductService {

    private final ProductRepository productRepository;

    public Product createProduct(@Valid ProductRequest productRequest) {
        if (productRepository.existsByName(productRequest.getName())) {
            throw new ProductNameAlreadyExistsException("Product with name " + productRequest.getName() + " already exists.");
        }
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        return productRepository.save(product);
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
