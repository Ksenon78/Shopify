package com.store.customer.controller;

import com.store.customer.DTO.ShopDTO;
import com.store.customer.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/shops")
public class ShopController {

    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping
    public List<ShopDTO> getAllShops() {
        return shopService.getAllShops();
    }

    @GetMapping("/{id}")
    public ShopDTO getShopById(@PathVariable("id") UUID id) {
        return shopService.getShopById(id);
    }

    @PostMapping
    public ResponseEntity<List<ShopDTO>> saveShops(@RequestBody List<ShopDTO> shopDTOs) {
        List<ShopDTO> savedShops = shopService.saveShops(shopDTOs);
        return new ResponseEntity<>(savedShops, HttpStatus.CREATED);
    }

    }