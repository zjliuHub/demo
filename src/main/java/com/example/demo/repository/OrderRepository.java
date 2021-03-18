package com.example.demo.repository;

import com.example.demo.domain.Order;
import com.example.demo.domain.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderRepository {
    Order save(Order order);
    List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
