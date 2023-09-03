package net.alienzone.orderservice.external.client;

import net.alienzone.orderservice.model.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE/payment")
public interface PaymentService {
    @PostMapping()
    ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);
}
