package com.store.customer.repository;

import com.store.customer.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}