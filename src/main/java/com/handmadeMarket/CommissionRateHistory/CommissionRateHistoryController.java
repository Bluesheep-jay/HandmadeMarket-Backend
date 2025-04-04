package com.handmadeMarket.CommissionRateHistory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commission-rate-history")
public class CommissionRateHistoryController {
    private final CommissionRateHistoryService commissionRateHistoryService;

    @Autowired
    public CommissionRateHistoryController(CommissionRateHistoryService commissionRateHistoryService) {
        this.commissionRateHistoryService = commissionRateHistoryService;
    }

    @GetMapping
    public List<CommissionRateHistory> getAll() {
        return commissionRateHistoryService.getAll();
    }

    @PostMapping
    public CommissionRateHistory create(@RequestBody CommissionRateHistory commissionRateHistory) {
        return commissionRateHistoryService.create(commissionRateHistory);
    }

    @PutMapping("/{id}")
    public CommissionRateHistory update(@PathVariable String id, @RequestBody CommissionRateHistory commissionRateHistory) {
        return commissionRateHistoryService.update(id, commissionRateHistory);
    }
}