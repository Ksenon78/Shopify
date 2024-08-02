package com.store.customer.service;

import com.store.customer.DTO.AddressDTO;
import com.store.customer.entity.Address;
import com.store.customer.entity.Customer;
import com.store.customer.entity.Shop;
import com.store.customer.repository.AddressRepository;
import com.store.customer.repository.CustomerRepository;
import com.store.customer.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;
    private final ShopRepository shopRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository, CustomerRepository customerRepository, ShopRepository shopRepository) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
        this.shopRepository = shopRepository;
    }

    public List<AddressDTO> getAllAddress() {
        return addressRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AddressDTO getAddressById(UUID id) {
        Optional<Address> addressOptional = addressRepository.findById(id);
        return addressOptional.map(this::convertToDTO).orElse(null);
    }

    public List<AddressDTO> saveAddresses(List<AddressDTO>addressDTOs) {
       List<Address> addresses = addressDTOs.stream()
        .map(this::convertToEntity)
                .collect(Collectors.toList());
     List<Address> savedAddresses = addressRepository.saveAll(addresses);
     return savedAddresses.stream()
             .map(this::convertToDTO)
             .collect(Collectors.toList());
    }

    private AddressDTO convertToDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressId(address.getAddressId());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setCity(address.getCity());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setPostalCode(address.getPostalCode());
        if (address.getCustomer() != null) {
            addressDTO.setCustomerId(address.getCustomer().getCustomerId());
        }
        if (address.getShop() != null) {
            addressDTO.setShopId(address.getShop().getShopId());
        }
        return addressDTO;
    }

    public Address convertToEntity(AddressDTO addressDTO) {
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
    }
}
