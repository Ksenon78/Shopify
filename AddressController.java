package com.store.customer.controller;

import com.store.customer.DTO.AddressDTO;
import com.store.customer.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public List<AddressDTO> getAllAddress() {
        return addressService.getAllAddress();
    }

    @GetMapping("/{id}")
    public AddressDTO getAddressById(@PathVariable("id") UUID id) {
        return addressService.getAddressById(id);
    }

    @PostMapping
    public ResponseEntity<List<AddressDTO>> saveAddresses(@RequestBody List<AddressDTO> addressDTOs) {
        List<AddressDTO> savedAddresses = addressService.saveAddresses(addressDTOs);
        return new ResponseEntity<>(savedAddresses, HttpStatus.CREATED);


    }

}
