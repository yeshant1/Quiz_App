package com.lpu.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class PaymentResponse {
    private String status;
    private String message;
    private String sessionId;
    private String sessionUrl;

    
    public static PaymentResponse create(String status, String message, String sessionId, String sessionUrl) {
        return new PaymentResponse(status, message, sessionId, sessionUrl);
    }
}
//Data To Frontend