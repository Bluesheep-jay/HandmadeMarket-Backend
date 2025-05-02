package com.handmadeMarket.VoucherUsage;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherUsageRepository extends MongoRepository<VoucherUsage, String> {
}