package com.handmadeMarket.CommissionRate;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommissionRateRepository extends MongoRepository<CommissionRate, String> {
}
