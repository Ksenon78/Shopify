package com.store.customer.controller;

import com.store.customer.DTO.CustomerDTO;
import com.store.customer.DTO.OrderDTO;
import com.store.customer.DTO.ProductDTO;
import com.store.customer.entity.Product;
import com.store.customer.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable("id") UUID id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public ResponseEntity<List<ProductDTO>> saveProducts(@RequestBody List<ProductDTO> productDTOs) {
        List<ProductDTO> savedProducts = productService.saveProducts(productDTOs);
        return new ResponseEntity<>(savedProducts, HttpStatus.CREATED);
    }
}
