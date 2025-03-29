package com.handmadeMarket.Transaction;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction create(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllByShopId(String shopId) {
        return transactionRepository.findByTransactionShopId(shopId);
    }
}
