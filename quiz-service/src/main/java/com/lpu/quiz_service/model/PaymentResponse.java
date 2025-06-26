package com.lpu.quiz_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // No-args constructor
@AllArgsConstructor // All-args constructor
public class PaymentResponse {
    private String status;
    private String message;
    private String sessionId;
    private String sessionUrl;

    // Static builder method
    public static PaymentResponse create(String status, String message, String sessionId, String sessionUrl) {
        return new PaymentResponse(status, message, sessionId, sessionUrl);
    }
}
