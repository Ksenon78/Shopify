package com.store.customer.service;

import com.store.customer.DTO.OrderDTO;
import com.store.customer.entity.Order;
import com.store.customer.entity.Customer;
import com.store.customer.entity.Shop;
import com.store.customer.entity.Address;
import com.store.customer.repository.CustomerRepository;
import com.store.customer.repository.ShopRepository;
import com.store.customer.repository.AddressRepository;
import com.store.customer.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ShopRepository shopRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository,
                        ShopRepository shopRepository, AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.shopRepository = shopRepository;
        this.addressRepository = addressRepository;
    }

    public Order convertToEntity(OrderDTO orderDTO) {
        Order order = new Order();
        order.setOrderId(orderDTO.getOrderId());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setStatus(orderDTO.getStatus());

        if (orderDTO.getCustomerId() != null) {
            Optional<Customer> customerOptional = customerRepository.findById(orderDTO.getCustomerId());
            customerOptional.ifPresent(order::setCustomer);
        }

        if (orderDTO.getShopId() != null) {
            Optional<Shop> shopOptional = shopRepository.findById(orderDTO.getShopId());
            shopOptional.ifPresent(order::setShop);
        }

        if (orderDTO.getAddressId() != null) {
            Optional<Address> addressOptional = addressRepository.findById(orderDTO.getAddressId());
            addressOptional.ifPresent(order::setAddress);
        }

        return order;
    }

    public List<OrderDTO> saveOrders(List<OrderDTO> orderDTOs) {
        List<Order> orders = orderDTOs.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());

        List<Order> savedOrders = orderRepository.saveAll(orders);

        return savedOrders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getAllOrders() {
        return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(UUID id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        return orderOptional.map(this::convertToDTO).orElse(null);
    }

    public Order getOrderEntityById(UUID id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        return orderOptional.orElse(null);
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setStatus(order.getStatus());

        if (order.getCustomer() != null) {
            orderDTO.setCustomerId(order.getCustomer().getCustomerId());
        }

        if (order.getShop() != null) {
            orderDTO.setShopId(order.getShop().getShopId());
        }

        if (order.getAddress() != null) {
            orderDTO.setAddressId(order.getAddress().getAddressId());
        }

        return orderDTO;
    }
}