package net.alienzone.paymentservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.alienzone.paymentservice.entiry.TransactionDetails;
import net.alienzone.paymentservice.model.PaymentRequest;
import net.alienzone.paymentservice.repostitory.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public long doPayment(PaymentRequest paymentRequest) {
        TransactionDetails transactionDetails = TransactionDetails.builder()
                .orderId(paymentRequest.getOrderId())
                .paymentMode(paymentRequest.getPaymentMethod().name())
                .status("SUCCESS")
                .referenceNumber(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount())
                .paymentDate(Instant.now())
                .build();
        paymentRepository.save(transactionDetails);
        log.info("Transaction completed successfully " + transactionDetails.getId());
        return transactionDetails.getId();
    }
}
