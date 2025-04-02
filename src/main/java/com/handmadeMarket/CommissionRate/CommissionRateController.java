package com.handmadeMarket.CommissionRate;

import com.handmadeMarket.Order.dto.MonthlyRevenueResult;
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


    @GetMapping("/{shopId}/{year}")
    public List<MonthlyRevenueResult> getMonthlyCommissionForYear(
            @PathVariable String shopId,
            @PathVariable int year) {
        return commissionRateService.getMonthlyCommissionForYear(shopId, year);
    }

    @PostMapping
    public CommissionRate create(@RequestBody CommissionRate commissionRate) {
        return commissionRateService.create(commissionRate);
    }


}
