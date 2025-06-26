package com.lpu.payment_service.service;

import com.lpu.payment_service.dto.PaymentRequest;
import com.lpu.payment_service.dto.PaymentResponse;
import com.lpu.payment_service.exception.CustomException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.api.key}")
    private String secretKey;

    public PaymentResponse checkout(PaymentRequest request) {
        Stripe.apiKey = secretKey;

        try {
            SessionCreateParams.LineItem.PriceData.ProductData productData =
                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                            .setName(request.getQuizTitle())
                            .build();

            SessionCreateParams.LineItem.PriceData priceData =
                    SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency(request.getCurrency() != null ? request.getCurrency() : "inr")
                            .setUnitAmount(request.getAmount()) // amount in paise
                            .setProductData(productData)
                            .build();

            SessionCreateParams.LineItem lineItem =
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(request.getQuantity())
                            .setPriceData(priceData)
                            .build();

            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:8090/quiz/payment-success") // your frontend URL
                    .setCancelUrl("http://localhost:8090/quiz/payment-cancel")
                    .addLineItem(lineItem)
                    .build();

            Session session = Session.create(params);

            return PaymentResponse.create(
                    "SUCCESS",
                    "Payment session created",
                    session.getId(),
                    session.getUrl()
            );

        } catch (StripeException e) {
            throw new CustomException("Failed to create payment session: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

////{
//  "line_items": [{
//    "price_data": {
//      "currency": "inr",
//      "unit_amount": 49900,
//      "product_data": {
//        "name": "Java Quiz"
//      }
//    },
//    "quantity": 1
//  }],
//  "mode": "payment",
//  "success_url": "...",
//  "cancel_url": "..."
//}
//SessionCreateParams
//└── LineItem
//    └── PriceData
//        └── ProductData (e.g., quiz name)

