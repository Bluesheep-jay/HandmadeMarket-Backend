package com.handmadeMarket.CommissionRateHistory;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommissionRateHistoryRepository extends MongoRepository<CommissionRateHistory, String> {
}
