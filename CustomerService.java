package com.store.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.store.customer.DTO.CustomerDTO;
import com.store.customer.entity.Customer;
import com.store.customer.entity.Shop;
import com.store.customer.repository.CustomerRepository;
import com.store.customer.repository.ShopRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ShopRepository shopRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           ShopRepository shopRepository,
                           JdbcTemplate jdbcTemplate) {
        this.customerRepository = customerRepository;
        this.shopRepository = shopRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(UUID id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        return customerOptional.map(this::convertToDTO).orElse(null);
    }

    public List<CustomerDTO> saveCustomers(List<CustomerDTO> customerDTOs) {
        List<Customer> customers = customerDTOs.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());

        List<Customer> savedCustomers = customerRepository.saveAll(customers);

        return savedCustomers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    public CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(customer.getCustomerId());
        customerDTO.setExternalId(customer.getExternalId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        if (customer.getShop() != null) {
            customerDTO.setShopId(customer.getShop().getShopId());
        }
        return customerDTO;
    }

    public Customer convertToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setCustomerId(customerDTO.getCustomerId());
        customer.setExternalId(customerDTO.getExternalId());
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());

        if (customerDTO.getShopId() != null) {
            Optional<Shop> shopOptional = shopRepository.findById(customerDTO.getShopId());
            shopOptional.ifPresent(customer::setShop);
        }

        return customer;
    }
}