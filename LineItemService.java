
        package com.store.customer.service;

import com.store.customer.DTO.LineItemDTO;
import com.store.customer.entity.LineItem;
import com.store.customer.entity.Order;
import com.store.customer.entity.Product;
import com.store.customer.entity.Shop;
import com.store.customer.repository.LineItemRepository;
import com.store.customer.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LineItemService {

    private final LineItemRepository lineItemRepository;
    private final ShopRepository shopRepository;
    private final OrderService orderService;
    private final ProductService productService;

    @Autowired
    public LineItemService(LineItemRepository lineItemRepository, ShopRepository shopRepository,
                           OrderService orderService, ProductService productService) {
        this.lineItemRepository = lineItemRepository;
        this.shopRepository = shopRepository;
        this.orderService = orderService;
        this.productService = productService;
    }

    public List<LineItemDTO> getAllLineItems() {
        return lineItemRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public LineItemDTO getLineItemById(UUID id) {
        Optional<LineItem> lineItemOptional = lineItemRepository.findById(id);
        return lineItemOptional.map(this::convertToDTO).orElse(null);
    }

    public List<LineItemDTO> saveLineItems(List<LineItemDTO> lineItemDTOs) {
        List<LineItem> lineItems = lineItemDTOs.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());

        List<LineItem> savedLineItems = lineItemRepository.saveAll(lineItems);

        return savedLineItems.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    private LineItemDTO convertToDTO(LineItem lineItem) {
        LineItemDTO lineItemDTO = new LineItemDTO();
        lineItemDTO.setLineItemId(lineItem.getLineItemId());
        lineItemDTO.setOrderId(lineItem.getOrder().getOrderId());
        lineItemDTO.setProductId(lineItem.getProduct().getProductId());
        lineItemDTO.setQuantity(lineItem.getQuantity());
        lineItemDTO.setPricePerUnit(lineItem.getPricePerUnit());
        lineItemDTO.setShopId(lineItem.getShop().getShopId());
        return lineItemDTO;
    }

    public LineItem convertToEntity(LineItemDTO lineItemDTO) {
        LineItem lineItem = new LineItem();
        lineItem.setLineItemId(lineItemDTO.getLineItemId());
        lineItem.setQuantity(lineItemDTO.getQuantity());
        lineItem.setPricePerUnit(lineItemDTO.getPricePerUnit());

        Order order = orderService.getOrderEntityById(lineItemDTO.getOrderId());  
        Product product = productService.getProductEntityById(lineItemDTO.getProductId());  

        lineItem.setOrder(order);
        lineItem.setProduct(product);

        Optional<Shop> shopOptional = shopRepository.findById(lineItemDTO.getShopId());
        shopOptional.ifPresent(lineItem::setShop);

        return lineItem;
    }
}
