package com.handmadeMarket.CommissionRate;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commission-rates")
public class CommissionRateController {
    private final CommissionRateService commissionRateService;

    public CommissionRateController(CommissionRateService commissionRateService) {
        this.commissionRateService = commissionRateService;
    }

    @GetMapping
    public List<CommissionRate> getAll() {
        return commissionRateService.getAll();
    }

    @PostMapping
    public CommissionRate create(@RequestBody CommissionRate commissionRate) {
        return commissionRateService.create(commissionRate);
    }
}
