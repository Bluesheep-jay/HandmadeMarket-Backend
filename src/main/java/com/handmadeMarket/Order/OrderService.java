package com.handmadeMarket.Order;

import com.handmadeMarket.OrderTemp.OrderTemp;
import com.handmadeMarket.OrderTemp.OrderTempRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderTempRepository orderTempRepository;

    public OrderService(OrderRepository orderRepository, OrderTempRepository orderTempRepository) {
        this.orderRepository = orderRepository;
        this.orderTempRepository = orderTempRepository;
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order create(Order order) {
        return orderRepository.save(order);
    }

    public List<OrderTemp> saveTempOrders(List<Order> orders) {
        List<OrderTemp> tempOrders = orders.stream().map(order -> {
            OrderTemp temp = new OrderTemp();
            temp.setOrderDetails(order.getOrderDetails());
            temp.setOrderDate(order.getOrderDate());
            temp.setExpectedDeliveryDate(order.getExpectedDeliveryDate());
            temp.setTotalPrice(order.getTotalPrice());
            temp.setOrderUserId(order.getOrderUserId());
            temp.setOrderStatusId(order.getOrderStatusId());
            temp.setOrderShopId(order.getOrderShopId());
            temp.setOrderPaymentMethodId(order.getOrderPaymentMethodId());
            temp.setOrderDeliveryAddressId(order.getOrderDeliveryAddressId());
            return temp;
        }).toList();

        return orderTempRepository.saveAll(tempOrders);
    }

    public List<Order> convertTempToRealOrders(List<String> tempOrderIds) {
        List<OrderTemp> tempOrders = orderTempRepository.findAllById(tempOrderIds);
        List<Order> realOrders = tempOrders.stream().map(temp -> {
            Order order = new Order();
            order.setOrderDetails(temp.getOrderDetails());
            order.setOrderDate(temp.getOrderDate());
            order.setExpectedDeliveryDate(temp.getExpectedDeliveryDate());
            order.setTotalPrice(temp.getTotalPrice());
            order.setOrderUserId(temp.getOrderUserId());
            order.setOrderStatusId(temp.getOrderStatusId());
            order.setOrderShopId(temp.getOrderShopId());
            order.setOrderPaymentMethodId(temp.getOrderPaymentMethodId());
            order.setOrderDeliveryAddressId(temp.getOrderDeliveryAddressId());
            return order;
        }).toList();

        orderTempRepository.deleteAllById(tempOrderIds);
        return orderRepository.saveAll(realOrders);
    }
}
