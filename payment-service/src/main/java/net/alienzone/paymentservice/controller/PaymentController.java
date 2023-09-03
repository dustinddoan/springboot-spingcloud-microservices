package net.alienzone.paymentservice.controller;

import lombok.AllArgsConstructor;
import net.alienzone.paymentservice.model.PaymentRequest;
import net.alienzone.paymentservice.model.PaymentResponse;
import net.alienzone.paymentservice.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping()
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest) {
        return new ResponseEntity<>(paymentService.doPayment(paymentRequest), HttpStatus.OK);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable("orderId") long orderId) {
        PaymentResponse paymentResponse = paymentService.getPaymentByOrderId(orderId);
        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }
}
