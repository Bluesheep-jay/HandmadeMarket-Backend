package com.handmadeMarket.VoucherUsage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voucher-usages")
public class VoucherUsageController {

    private final VoucherUsageService voucherUsageService;

    @Autowired
    public VoucherUsageController(VoucherUsageService voucherUsageService) {
        this.voucherUsageService = voucherUsageService;
    }

    @GetMapping
    public List<VoucherUsage> getAllVoucherUsages() {
        return voucherUsageService.getAllVoucherUsages();
    }

    @GetMapping("/{id}")
    public VoucherUsage getVoucherUsageById(@PathVariable String id) {
        return voucherUsageService.getVoucherUsageById(id);
    }

    @PostMapping
    public VoucherUsage createVoucherUsage(@RequestBody VoucherUsage voucherUsage) {
        return voucherUsageService.createVoucherUsage(voucherUsage);
    }

    @PutMapping("/{id}")
    public VoucherUsage updateVoucherUsage(@PathVariable String id, @RequestBody VoucherUsage voucherUsage) {
        return voucherUsageService.updateVoucherUsage(id, voucherUsage);
    }

    @DeleteMapping("/{id}")
    public void deleteVoucherUsage(@PathVariable String id) {
        voucherUsageService.deleteVoucherUsage(id);
    }
}