package com.handmadeMarket.Voucher;

import com.handmadeMarket.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    public List<Voucher> getVouchersByShopId(String shopId) {
        return voucherRepository.findByShopId(shopId);
    }

    public List<Voucher> getVouchersByPlatform() {
        return voucherRepository.findByShopIdIsNull();
    }
    public Voucher getVoucherById(String id) {
        return voucherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Voucher not found with id: " + id));
    }

    public Voucher createVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    public void useVoucher(String id) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Voucher not found with id: " + id));
        if (voucher.getUsedCount() >= voucher.getUsageLimit()) {
            throw new IllegalStateException("Voucher usage limit reached");
        }
        voucher.setUsedCount(voucher.getUsedCount() + 1);
        if(voucher.getUsedCount() >= voucher.getUsageLimit()) {
            voucher.setStatus("EXPIRED");
        }
        voucherRepository.save(voucher);
    }
    public Voucher updateVoucher(String id, Voucher updatedVoucher) {
        Voucher existingVoucher = voucherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Voucher not found with id: " + id));
        updatedVoucher.setId(id);
        return voucherRepository.save(updatedVoucher);
    }

    public void deleteVoucher(String id) {
        Voucher existingVoucher = voucherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Voucher not found with id: " + id));
        voucherRepository.delete(existingVoucher);
    }


}