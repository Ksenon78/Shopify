package com.store.customer.controller;

import com.store.customer.DTO.CustomerDTO;
import com.store.customer.DTO.LineItemDTO;
import com.store.customer.entity.LineItem;
import com.store.customer.service.LineItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/lineItems")
public class LineItemController {

    private final LineItemService lineItemService;

    @Autowired
    public LineItemController(LineItemService lineItemService) {
        this.lineItemService = lineItemService;
    }

    @GetMapping
    public List<LineItemDTO> getAllLineItem() {
        return lineItemService.getAllLineItems();
    }

    @GetMapping("/{id}")
    public LineItemDTO getLineItemById(@PathVariable("id") UUID id) {
        return lineItemService.getLineItemById(id);
    }

    @PostMapping
    public ResponseEntity<List<LineItemDTO>> saveLineItems(@RequestBody List<LineItemDTO> lineItemDTOs) {
        List<LineItemDTO> savedLineItems = lineItemService.saveLineItems(lineItemDTOs);
        return new ResponseEntity<>(savedLineItems, HttpStatus.CREATED);
    }

    }
