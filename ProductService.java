package com.store.customer.service;

import com.store.customer.DTO.ProductDTO;
import com.store.customer.entity.Product;
import com.store.customer.entity.Shop;
import com.store.customer.repository.ProductRepository;
import com.store.customer.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ShopRepository shopRepository) {
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(UUID id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.map(this::convertToDTO).orElse(null);
    }

    public List<ProductDTO> saveProducts(List<ProductDTO> productDTOs) {
        List<Product> products = productDTOs.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());

        List<Product> savedProducts = productRepository.saveAll(products);

        return savedProducts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(product.getProductId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setQuantityAvailable(product.getQuantityAvailable());
        if (product.getShop() != null) {
            productDTO.setShopId(product.getShop().getShopId());
        }
        return productDTO;
    }

    public Product getProductEntityById(UUID id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setProductId(productDTO.getProductId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantityAvailable(productDTO.getQuantityAvailable());

        Optional<Shop> shopOptional = shopRepository.findById(productDTO.getShopId());
        shopOptional.ifPresent(product::setShop);

        return product;
    }
}