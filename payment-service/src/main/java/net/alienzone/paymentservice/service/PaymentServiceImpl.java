package net.alienzone.paymentservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.alienzone.paymentservice.entiry.TransactionDetails;
import net.alienzone.paymentservice.model.PaymentMethod;
import net.alienzone.paymentservice.model.PaymentRequest;
import net.alienzone.paymentservice.model.PaymentResponse;
import net.alienzone.paymentservice.repostitory.TransactionDetailsRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    private final TransactionDetailsRepository transactionDetailsRepository;

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
        transactionDetailsRepository.save(transactionDetails);
        log.info("Transaction completed successfully " + transactionDetails.getId());
        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentByOrderId(long orderId) {
        TransactionDetails transactionDetails = transactionDetailsRepository
                .findByOrderId(orderId);

        log.info("Transaction details " + transactionDetails.toString());

        PaymentResponse paymentResponse = PaymentResponse.builder()
                .status(transactionDetails.getStatus())
                .amount(transactionDetails.getAmount())
                .orderId(transactionDetails.getOrderId())
                .paymentDate(transactionDetails.getPaymentDate())
                .paymentMethod(PaymentMethod.valueOf(transactionDetails.getPaymentMode()))
                .build();
        return paymentResponse;
    }
}
