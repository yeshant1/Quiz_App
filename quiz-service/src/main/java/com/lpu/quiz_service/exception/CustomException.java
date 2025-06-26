// File: com.lpu.quiz_service.exception.CustomException.java
package com.lpu.quiz_service.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class CustomException extends RuntimeException {
    private HttpStatus status;
    public CustomException(String message,HttpStatus status) {
        super(message);
        this.status=status;
       
    }

   
}
