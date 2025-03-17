package com.handmadeMarket.PaymentMethod;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository  extends MongoRepository<PaymentMethod, String> {
}
