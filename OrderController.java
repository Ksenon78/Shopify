package com.store.customer.controller;

import com.store.customer.DTO.AddressDTO;
import com.store.customer.DTO.CustomerDTO;
import com.store.customer.DTO.OrderDTO;
import com.store.customer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable("id") UUID id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public ResponseEntity<List<OrderDTO>> saveOrders(@RequestBody List<OrderDTO> orderDTOs) {
        List<OrderDTO> savedOrders = orderService.saveOrders(orderDTOs);
        return new ResponseEntity<>(savedOrders, HttpStatus.CREATED);
    }
}