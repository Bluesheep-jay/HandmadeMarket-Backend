package com.handmadeMarket.OrderTemp;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderTempRepository extends MongoRepository<OrderTemp, String> {
}
