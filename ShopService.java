package com.store.customer.service;

import com.store.customer.DTO.ShopDTO;
import com.store.customer.entity.Shop;
import com.store.customer.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.store.customer.entity.Customer;
import com.store.customer.entity.Order;
import com.store.customer.entity.Address;
import com.store.customer.entity.Product;
import com.store.customer.entity.LineItem;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ShopService {

    private final ShopRepository shopRepository;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final AddressService addressService;
    private final ProductService productService;
    private final LineItemService lineItemService;

    @Autowired
    public ShopService(ShopRepository shopRepository, CustomerService customerService, OrderService orderService,
                       AddressService addressService, ProductService productService, LineItemService lineItemService) {
        this.shopRepository = shopRepository;
        this.customerService = customerService;
        this.orderService = orderService;
        this.addressService = addressService;
        this.productService = productService;
        this.lineItemService = lineItemService;
    }

    public List<ShopDTO> getAllShops() {
        return StreamSupport.stream(shopRepository.findAll().spliterator(), false)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ShopDTO getShopById(UUID id) {
        Optional<Shop> shopOptional = shopRepository.findById(id);
        return shopOptional.map(this::convertToDTO).orElse(null);
    }

    public List<ShopDTO> saveShops(List<ShopDTO> shopDTOs) {
        List<Shop> shops = shopDTOs.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());

        List<Shop> savedShops = shopRepository.saveAll(shops);

        return savedShops.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ShopDTO convertToDTO(Shop shop) {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setShopId(shop.getShopId());
        shopDTO.setName(shop.getName());
        shopDTO.setCustomerIds(shop.getCustomers().stream().map(Customer::getCustomerId).collect(Collectors.toList()));
        shopDTO.setOrderIds(shop.getOrders().stream().map(Order::getOrderId).collect(Collectors.toList()));
        shopDTO.setAddressIds(shop.getAddresses().stream().map(Address::getAddressId).collect(Collectors.toList()));
        shopDTO.setProductIds(shop.getProducts().stream().map(Product::getProductId).collect(Collectors.toList()));
        shopDTO.setLineItemIds(shop.getLineItems().stream().map(LineItem::getLineItemId).collect(Collectors.toList()));
        return shopDTO;
    }

    private Shop convertToEntity(ShopDTO shopDTO) {
        Shop shop = new Shop();
        shop.setShopId(shopDTO.getShopId());
        shop.setName(shopDTO.getName());

        List<Customer> customers = shopDTO.getCustomerIds().stream()
                .map(customerService::getCustomerById)
                .filter(Objects::nonNull)
                .map(customerService::convertToEntity)
                .collect(Collectors.toList());
        shop.setCustomers(customers);

        List<Order> orders = shopDTO.getOrderIds().stream()
                .map(orderService::getOrderById)
                .filter(Objects::nonNull)
                .map(orderService::convertToEntity)
                .collect(Collectors.toList());
        shop.setOrders(orders);

        List<Address> addresses = shopDTO.getAddressIds().stream()
                .map(addressService::getAddressById)
                .filter(Objects::nonNull)
                .map(addressService::convertToEntity)
                .collect(Collectors.toList());
        shop.setAddresses(addresses);

        List<Product> products = shopDTO.getProductIds().stream()
                .map(productService::getProductById)
                .filter(Objects::nonNull)
                .map(productService::convertToEntity)
                .collect(Collectors.toList());
        shop.setProducts(products);

        List<LineItem> lineItems = shopDTO.getLineItemIds().stream()
                .map(lineItemService::getLineItemById)
                .filter(Objects::nonNull)
                .map(lineItemService::convertToEntity)
                .collect(Collectors.toList());
        shop.setLineItems(lineItems);

        return shop;
    }
}