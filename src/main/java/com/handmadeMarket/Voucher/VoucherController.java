package com.handmadeMarket.Voucher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vouchers")
public class VoucherController {

    private final VoucherService voucherService;

    @Autowired
    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/by-shop/{shopId}")
    public List<Voucher> getVouchersByShopId(@PathVariable String shopId) {
        return voucherService.getVouchersByShopId(shopId);
    }

    @GetMapping("/by-platform")
    public List<Voucher> getVouchersByPlatform() {
        return voucherService.getVouchersByPlatform();
    }

    @GetMapping
    public List<Voucher> getAllVouchers() {
        return voucherService.getAllVouchers();
    }

    @GetMapping("/{id}")
    public Voucher getVoucherById(@PathVariable String id) {
        return voucherService.getVoucherById(id);
    }

    @PostMapping
    public Voucher createVoucher(@RequestBody Voucher voucher) {
        return voucherService.createVoucher(voucher);
    }

    @PutMapping("/use/{id}")
    public void useVoucher(@PathVariable String id) {
        voucherService.useVoucher(id);
    }

    @PutMapping("/{id}")
    public Voucher updateVoucher(@PathVariable String id, @RequestBody Voucher voucher) {
        return voucherService.updateVoucher(id, voucher);
    }

    @DeleteMapping("/{id}")
    public void deleteVoucher(@PathVariable String id) {
        voucherService.deleteVoucher(id);
    }
}