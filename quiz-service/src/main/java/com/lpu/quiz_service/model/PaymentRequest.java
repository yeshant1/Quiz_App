package com.lpu.quiz_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // No-args constructor
@AllArgsConstructor // All-args constructor
public class PaymentRequest {
    private String quizTitle;
    private Long amount;       // In paise (₹100 = 10000)
    private Long quantity;
    private String currency;   // e.g., "inr"
}
