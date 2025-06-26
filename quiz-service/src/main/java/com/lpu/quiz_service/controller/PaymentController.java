package com.lpu.quiz_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PaymentController {

    @GetMapping("/quiz/payment-success")
   

    public String paymentSuccess() {
      
        return "redirect:/payment-success.html"; 
    }

    @GetMapping("/quiz/payment-cancel")

    public String paymentCancel() {
       
    	
        return "redirect:/payment-cancel.html"; 
    }
}
