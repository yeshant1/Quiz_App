package com.lpu.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class PaymentRequest {
    private String quizTitle;
    private Long amount;       
    private Long quantity;
    private String currency;  
}
//Data sent from the frontend to backend 