package com.handmadeMarket.CommissionRateHistory;

import com.handmadeMarket.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommissionRateHistoryService {

    private final CommissionRateHistoryRepository commissionRateHistoryRepository;

    @Autowired
    public CommissionRateHistoryService(CommissionRateHistoryRepository commissionRateHistoryRepository) {
        this.commissionRateHistoryRepository = commissionRateHistoryRepository;
    }

    public List<CommissionRateHistory> getAll() {
        return commissionRateHistoryRepository.findAll();
    }

    public CommissionRateHistory create(CommissionRateHistory commissionRateHistory) {
        return commissionRateHistoryRepository.save(commissionRateHistory);
    }


    public CommissionRateHistory update(String id, CommissionRateHistory commissionRateHistory) {
        CommissionRateHistory existingHistory = commissionRateHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CommissionRateHistory not found"));
        existingHistory.setStartDate(commissionRateHistory.getStartDate());
        existingHistory.setEndDate(commissionRateHistory.getEndDate());
        existingHistory.setCommissionRates(commissionRateHistory.getCommissionRates());
        return commissionRateHistoryRepository.save(existingHistory);
    }
}