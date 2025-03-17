package com.handmadeMarket.Order;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping
    public List<Order> getAll() {
        return orderService.getAll();
    }

    @PostMapping
    public Order create(@RequestBody Order order) {
        return orderService.create(order);
    }
}
