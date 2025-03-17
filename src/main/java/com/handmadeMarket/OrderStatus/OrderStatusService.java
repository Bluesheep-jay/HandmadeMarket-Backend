package com.handmadeMarket.OrderStatus;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;

    public OrderStatusService(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    public OrderStatus create(OrderStatus orderStatus) {
        return orderStatusRepository.save(orderStatus);
    }

    public OrderStatus getById(String id) {
        return orderStatusRepository.findById(id).orElse(null);
    }

    public List<OrderStatus> getAll() {
        return orderStatusRepository.findAll();
    }
}
