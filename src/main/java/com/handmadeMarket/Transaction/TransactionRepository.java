package com.handmadeMarket.Transaction;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findByTransactionShopId(String shopId);
}
