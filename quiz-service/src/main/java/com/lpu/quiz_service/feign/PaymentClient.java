package com.lpu.quiz_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.lpu.quiz_service.model.PaymentRequest;
import com.lpu.quiz_service.model.PaymentResponse;

@FeignClient("PAYMENT-SERVICE")
public interface PaymentClient {

    @PostMapping("/api/payment/checkout")
    ResponseEntity<PaymentResponse> checkout(PaymentRequest request);
}