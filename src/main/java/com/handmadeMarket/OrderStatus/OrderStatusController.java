package com.handmadeMarket.OrderStatus;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-statuses")
public class OrderStatusController {
    private final OrderStatusService orderStatusService;

    public OrderStatusController(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    @PostMapping
    public OrderStatus create( @RequestBody OrderStatus orderStatus) {
        return orderStatusService.create(orderStatus);
    }

    @GetMapping("/{id}")
    public OrderStatus getById(@PathVariable String id) {
        return orderStatusService.getById(id);
    }

    @GetMapping
    public List<OrderStatus> getAll() {
        return orderStatusService.getAll();
    }

}
