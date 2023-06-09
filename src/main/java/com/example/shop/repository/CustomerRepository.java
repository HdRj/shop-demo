package com.example.shop.repository;

import com.example.shop.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhone(String phone);
}
