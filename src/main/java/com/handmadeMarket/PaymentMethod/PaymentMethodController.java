package com.handmadeMarket.PaymentMethod;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @PostMapping
    public PaymentMethod create(@RequestBody PaymentMethod paymentMethod) {
        return paymentMethodService.create(paymentMethod);
    }

    @GetMapping("/{id}")
    public PaymentMethod getById(@PathVariable String id) {
        return paymentMethodService.getById(id);
    }

    @GetMapping
    public List<PaymentMethod> getAll() {
        return paymentMethodService.getAll();
    }


}
