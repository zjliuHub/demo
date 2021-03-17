package com.example.demo.repository;

import com.example.demo.domain.Order;

public interface OrderRepository {
    Order save(Order order);
}
