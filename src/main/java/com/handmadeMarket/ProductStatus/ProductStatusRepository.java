package com.handmadeMarket.ProductStatus;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductStatusRepository extends MongoRepository<ProductStatus, String> {
}
