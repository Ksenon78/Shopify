package com.store.customer.handler;

import com.store.customer.DTO.*;
import com.store.customer.entity.*;
import com.store.customer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ShopifyHandlerService {

    private static final Logger logger = LoggerFactory.getLogger(ShopifyHandlerService.class);


    @Value("${shopify.api.key}")
    private String shopifyApiKey;

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;
    private final LineItemRepository lineItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final RestTemplate restTemplate;

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Shopify-Access-Token", shopifyApiKey);
        return headers;
    }



    @Autowired
    public ShopifyHandlerService(AddressRepository addressRepository,
                                 CustomerRepository customerRepository,
                                 LineItemRepository lineItemRepository,
                                 OrderRepository orderRepository,
                                 ProductRepository productRepository,
                                 ShopRepository shopRepository,
                                 RestTemplate restTemplate) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
        this.lineItemRepository = lineItemRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public void fetchDataAndSaveToDatabase() {
        fetchAndSaveCustomers();
        fetchAndSaveProducts();
        fetchAndSaveAddresses();
        fetchAndSaveOrders();
        fetchAndSaveLineItems();
    }

    private void fetchAndSaveCustomers() {
        String url = "https://920586-d6.myshopify.com/admin/api/2024-04/customers.json?status=any";
        ResponseEntity<Map<String, List<CustomerDTO>>> response = restTemplate.exchange(
                url, HttpMethod.GET, new HttpEntity<>(createHeaders()), new ParameterizedTypeReference<Map<String, List<CustomerDTO>>>() {}
        );

        logger.info("Customer API call response: {}", response);

        if (response.getStatusCode() == HttpStatus.OK) {
            List<CustomerDTO> customerDTOList = response.getBody().get("customers");
            if (customerDTOList != null) {
                logger.info("Fetched customers: {}", customerDTOList);
                List<Customer> customerList = customerDTOList.stream().map(customerDTO -> {
                    Customer customer = new Customer();
                    customer.setCustomerId(customerDTO.getCustomerId());
                    customer.setName(customerDTO.getName());
                    customer.setEmail(customerDTO.getEmail());

                    Shop shopify = shopRepository.findByName("shopify").orElseGet(() -> {
                        Shop newShop = new Shop();
                        newShop.setName("shopify");
                        return shopRepository.save(newShop);
                    });
                    customer.setShop(shopify);

                    return customer;
                }).collect(Collectors.toList());

                customerRepository.saveAll(customerList);
            } else {
                logger.warn("Customer list is null");
            }
        } else {
            logger.error("Customer API call failed: {}", response.getStatusCode());
        }
    }

    private void fetchAndSaveProducts() {
        String url = "https://920586-d6.myshopify.com/admin/api/2024-04/products.json?status=any";
        ResponseEntity<Map<String, List<ProductDTO>>> response = restTemplate.exchange(
                url, HttpMethod.GET, new HttpEntity<>(createHeaders()), new ParameterizedTypeReference<Map<String, List<ProductDTO>>>() {}
        );

        logger.info("Product API call response: {}", response);

        if (response.getStatusCode() == HttpStatus.OK) {
            List<ProductDTO> productDTOList = response.getBody().get("products");
            if (productDTOList != null) {
                logger.info("Fetched products: {}", productDTOList);
                List<Product> productList = productDTOList.stream().map(productDTO -> {
                    Product product = new Product();
                    product.setProductId(productDTO.getProductId());
                    product.setName(productDTO.getName());
                    product.setDescription(productDTO.getDescription());
                    product.setPrice(productDTO.getPrice());
                    product.setQuantityAvailable(productDTO.getQuantityAvailable());

                    Optional<Shop> shopOptional = shopRepository.findById(productDTO.getShopId());
                    shopOptional.ifPresent(product::setShop);

                    return product;
                }).collect(Collectors.toList());

                productRepository.saveAll(productList);
            } else {
                logger.warn("Product list is null");
            }
        } else {
            logger.error("Product API call failed: {}", response.getStatusCode());
            logger.error("Response body: {}", response.getBody());
        }
    }

    private void fetchAndSaveAddresses() {
        String url = "https://920586-d6.myshopify.com/admin/api/2024-04/addresses.json?status=any";
        ResponseEntity<Map<String, List<AddressDTO>>> response = restTemplate.exchange(
                url, HttpMethod.GET, new HttpEntity<>(createHeaders()), new ParameterizedTypeReference<Map<String, List<AddressDTO>>>() {}
        );

        logger.info("Address API call response: {}", response);

        if (response.getStatusCode() == HttpStatus.OK) {
            List<AddressDTO> addressDTOList = response.getBody().get("addresses");
            if (addressDTOList != null) {
                logger.info("Fetched addresses: {}", addressDTOList);
                List<Address> addressList = addressDTOList.stream().map(addressDTO -> {
                    Address address = new Address();
                    address.setAddressId(addressDTO.getAddressId());
                    address.setStreet(addressDTO.getStreet());
                    address.setCity(addressDTO.getCity());
                    address.setCountry(addressDTO.getCountry());
                    address.setPostalCode(addressDTO.getPostalCode());

                    Optional<Customer> customerOptional = customerRepository.findById(addressDTO.getCustomerId());
                    customerOptional.ifPresent(address::setCustomer);

                    Optional<Shop> shopOptional = shopRepository.findById(addressDTO.getShopId());
                    shopOptional.ifPresent(address::setShop);

                    return address;
                }).collect(Collectors.toList());

                addressRepository.saveAll(addressList);
            } else {
                logger.warn("Address list is null");
            }
        } else {
            logger.error("Address API call failed: {}", response.getStatusCode());
        }
    }

    private void fetchAndSaveOrders() {
        String url = "https://920586-d6.myshopify.com/admin/api/2024-04/orders.json?status=any";
        ResponseEntity<Map<String, List<OrderDTO>>> response = restTemplate.exchange(
                url, HttpMethod.GET, new HttpEntity<>(createHeaders()), new ParameterizedTypeReference<Map<String, List<OrderDTO>>>() {}
        );

        logger.info("Order API call response: {}", response);

        if (response.getStatusCode() == HttpStatus.OK) {
            List<OrderDTO> orderDTOList = response.getBody().get("orders");
            if (orderDTOList != null) {
                logger.info("Fetched orders: {}", orderDTOList);
                List<Order> orderList = orderDTOList.stream().map(orderDTO -> {
                    Order order = new Order();
                    order.setOrderId(orderDTO.getOrderId());
                    order.setOrderDate(orderDTO.getOrderDate());
                    order.setStatus(orderDTO.getStatus());

                    Optional<Customer> customerOptional = customerRepository.findById(orderDTO.getCustomerId());
                    customerOptional.ifPresent(order::setCustomer);

                    Optional<Shop> shopOptional = shopRepository.findById(orderDTO.getShopId());
                    shopOptional.ifPresent(order::setShop);

                    Optional<Address> addressOptional = addressRepository.findById(orderDTO.getAddressId());
                    addressOptional.ifPresent(order::setAddress);

                    return order;
                }).collect(Collectors.toList());

                orderRepository.saveAll(orderList);
            } else {
                logger.warn("Order list is null");
            }
        } else {
            logger.error("Order API call failed: {}", response.getStatusCode());
        }
    }

    private void fetchAndSaveLineItems() {
        String url = "https://920586-d6.myshopify.com/admin/api/2024-04/line_items.json?status=any";
        ResponseEntity<Map<String, List<LineItemDTO>>> response = restTemplate.exchange(
                url, HttpMethod.GET, new HttpEntity<>(createHeaders()), new ParameterizedTypeReference<Map<String, List<LineItemDTO>>>() {}
        );

        logger.info("Line Item API call response: {}", response);

        if (response.getStatusCode() == HttpStatus.OK) {
            List<LineItemDTO> lineItemDTOList = response.getBody().get("line_items");
            if (lineItemDTOList != null) {
                logger.info("Fetched line items: {}", lineItemDTOList);

                List<LineItem> lineItemList = lineItemDTOList.stream().map(lineItemDTO -> {
                    LineItem lineItem = new LineItem();
                    lineItem.setLineItemId(lineItemDTO.getLineItemId());

                    Order order = orderRepository.findById(lineItemDTO.getOrderId()).orElse(null);
                    lineItem.setOrder(order);

                    Product product = productRepository.findById(lineItemDTO.getProductId()).orElse(null);
                    lineItem.setProduct(product);

                    lineItem.setQuantity(lineItemDTO.getQuantity());
                    lineItem.setPricePerUnit(lineItemDTO.getPricePerUnit());


                    return lineItem;
                }).collect(Collectors.toList());

                lineItemRepository.saveAll(lineItemList);
            } else {
                logger.warn("Line item list is null");
            }
        } else {
            logger.error("Line Item API call failed: {}", response.getStatusCode());
        }
    }
}
