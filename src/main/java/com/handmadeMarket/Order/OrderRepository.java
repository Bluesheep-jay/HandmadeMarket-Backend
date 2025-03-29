package com.handmadeMarket.Order;

import com.handmadeMarket.Order.dto.OrderWithProduct;
import com.handmadeMarket.OrderTemp.OrderTemp;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByOrderUserId(String userId);
}
