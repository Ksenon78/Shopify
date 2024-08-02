
package com.store.customer.controller;

import com.store.customer.DTO.CustomerDTO;
import com.store.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable("id") UUID id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public ResponseEntity<List<CustomerDTO>> saveCustomers(@RequestBody List<CustomerDTO> customerDTOs) {
        List<CustomerDTO> savedCustomers = customerService.saveCustomers(customerDTOs);
        return new ResponseEntity<>(savedCustomers, HttpStatus.CREATED);
    }
}