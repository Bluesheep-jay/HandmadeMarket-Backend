package com.handmadeMarket.Order;

import com.handmadeMarket.Order.dto.DailyRevenueResult;
import com.handmadeMarket.Order.dto.MonthlyRevenueResult;
import com.handmadeMarket.Order.dto.OrderWithProduct;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/with-products/customer/{userId}")
    public List<OrderWithProduct> getOrdersWithProductsByUserId(@PathVariable String userId) {
        return orderService.getOrdersWithProductsByUserId(userId);
    }

    @GetMapping("/with-products/shop/{shopId}")
    public List<OrderWithProduct> getOrdersWithProductsByShopId(@PathVariable String shopId) {
        return orderService.getOrdersWithProductsByShopId(shopId);
    }


    @GetMapping("/daily-revenue-for-month/{shopId}/{year}/{month}")
    public List<DailyRevenueResult> getDailyRevenueForMonth(
            @PathVariable String shopId,
            @PathVariable int year,
            @PathVariable int month) {
        return orderService.getDailyRevenueForMonth(shopId, year, month);
    }

    @GetMapping("/monthly-revenue-for-year/{shopId}/{year}")
    public List<MonthlyRevenueResult> getMonthlyRevenueForYear(
            @PathVariable String shopId,
            @PathVariable int year) {
        return orderService.getMonthlyRevenueForYear(shopId, year);
    }

    @GetMapping
    public List<Order> getAll() {
        return orderService.getAll();
    }

    @PostMapping
    public List<Order> create(@RequestBody List<Order> orders) {
        return orderService.create(orders);
    }

    @PutMapping("/{orderId}/status/{statusId}")
    public Order updateStatus(@PathVariable String orderId, @PathVariable String statusId) {
        return orderService.updateOrderStatus(orderId, statusId);
    }
}
