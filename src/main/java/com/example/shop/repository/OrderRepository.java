package com.example.shop.repository;

import com.example.shop.model.entity.Customer;
import com.example.shop.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByCustomer(Customer customer);

}
