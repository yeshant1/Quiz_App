package com.lpu.payment_service.controller;

import com.lpu.payment_service.dto.PaymentRequest;
import com.lpu.payment_service.dto.PaymentResponse;
import com.lpu.payment_service.service.StripeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final StripeService stripeService;

    public PaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<PaymentResponse> checkout(@RequestBody PaymentRequest paymentRequest) {
        PaymentResponse response = stripeService.checkout(paymentRequest);
        return ResponseEntity.ok(response);
    }
}