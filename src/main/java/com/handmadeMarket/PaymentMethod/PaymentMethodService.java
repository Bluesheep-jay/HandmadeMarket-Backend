package com.handmadeMarket.PaymentMethod;

import com.handmadeMarket.Exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public PaymentMethod create(PaymentMethod paymentMethod) {
        return paymentMethodRepository.save(paymentMethod);
    }

    public List<PaymentMethod> getAll() {
        return paymentMethodRepository.findAll();
    }

    public PaymentMethod getById(String id) {
        return paymentMethodRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("PaymentMethod not found with id: " + id));
    }
}
