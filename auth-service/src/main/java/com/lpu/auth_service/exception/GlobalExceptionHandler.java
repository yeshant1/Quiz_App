package com.lpu.auth_service.exception;
 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
 
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;
 
 
@RestControllerAdvice
public class GlobalExceptionHandler {
//
//    @ExceptionHandler(CustomException.class)
//    public String handleCustomException(CustomException ex) {
//        return ex.getMessage();
//    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentials(BadCredentialsException ex) {
    	return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Incorrect username or password.");
    }
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleHttpStatus(CustomException ex){
//    	return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
//    	Map<String,HttpStatus> res = new HashMap<>();
//    	res.put(ex.getMessage(),ex.getStatus());
    	return new ResponseEntity<>(ex.getMessage(),ex.getStatus());
    }
}