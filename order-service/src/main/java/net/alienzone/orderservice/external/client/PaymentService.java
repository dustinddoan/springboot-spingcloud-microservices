package net.alienzone.orderservice.external.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import net.alienzone.orderservice.exception.CustomException;
import net.alienzone.orderservice.model.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CircuitBreaker(name="external", fallbackMethod = "doPaymentFallback")
@FeignClient(name = "PAYMENT-SERVICE/payment")
public interface PaymentService {
    @PostMapping()
    ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);

    default void doPaymentFallback(Exception exception) {
        throw new CustomException(
                "Payment service is down",
                "UNAVAILABLE",
                500);
    }
}
