package com.handmadeMarket.VoucherUsage;

import com.handmadeMarket.Exception.ResourceNotFoundException;
import com.handmadeMarket.Voucher.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherUsageService {
    private final VoucherService voucherService;
    private final VoucherUsageRepository voucherUsageRepository;

    @Autowired
    public VoucherUsageService(VoucherUsageRepository voucherUsageRepository, VoucherService voucherService) {
        this.voucherUsageRepository = voucherUsageRepository;
        this.voucherService = voucherService;
    }

    public List<VoucherUsage> getAllVoucherUsages() {
        return voucherUsageRepository.findAll();
    }

    public VoucherUsage getVoucherUsageById(String id) {
        return voucherUsageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VoucherUsage not found with id: " + id));
    }

    public VoucherUsage createVoucherUsage(VoucherUsage voucherUsage) {
        voucherService.useVoucher(voucherUsage.getVoucherId());
        return voucherUsageRepository.save(voucherUsage);
    }

    public VoucherUsage updateVoucherUsage(String id, VoucherUsage updatedVoucherUsage) {
        VoucherUsage existingVoucherUsage = voucherUsageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VoucherUsage not found with id: " + id));
        updatedVoucherUsage.setId(id);
        return voucherUsageRepository.save(updatedVoucherUsage);
    }

    public void deleteVoucherUsage(String id) {
        VoucherUsage existingVoucherUsage = voucherUsageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VoucherUsage not found with id: " + id));
        voucherUsageRepository.delete(existingVoucherUsage);
    }
}