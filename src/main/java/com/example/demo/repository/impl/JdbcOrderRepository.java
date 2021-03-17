package com.example.demo.repository.impl;

import com.example.demo.domain.Order;
import com.example.demo.domain.Taco;
import com.example.demo.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private SimpleJdbcInsert orderInsert;
    private SimpleJdbcInsert orderTacoInsert;
    private ObjectMapper objectMapper;

    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
        this.orderInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("taco_order").usingGeneratedKeyColumns("id");
        this.orderTacoInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("taco_order_tacos");
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Timestamp(new Date().getTime()));
        long orderId = saveOrderDetails(order);
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();
        for (Taco taco : tacos) {
            saveTacoToOrder(taco, orderId);
        }
        return order;
    }

    private void saveTacoToOrder(Taco taco, long orderId) {
        Map<String, Object> values = new HashMap<>();
        values.put("order_id", orderId);
        values.put("tacos_id", taco.getId());
        orderTacoInsert.execute(values);
    }

    private long saveOrderDetails(Order order) {
        Map<String, Object> values = objectMapper.convertValue(order, Map.class);
        values.put("placed_at", order.getPlacedAt());
        long orderId = orderInsert.executeAndReturnKey(values).longValue();
        return orderId;
    }
}
