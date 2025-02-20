package com.handmadeMarket.Shop;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ShopRepository extends MongoRepository<Shop, String> {

    List<Shop> findByIdIn(List<String> idList);
}
