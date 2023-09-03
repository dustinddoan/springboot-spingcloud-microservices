package net.alienzone.paymentservice.service;

import net.alienzone.paymentservice.model.PaymentRequest;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);
}
