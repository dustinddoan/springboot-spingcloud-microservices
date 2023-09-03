package net.alienzone.paymentservice.service;

import net.alienzone.paymentservice.model.PaymentRequest;
import net.alienzone.paymentservice.model.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentByOrderId(long orderId);
}
