package net.alienzone.orderservice.external.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.alienzone.orderservice.model.PaymentMethod;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private long paymentId;
    private String status;
    private PaymentMethod paymentMethod;
    private long amount;
    private Instant paymentDate;
    private long orderId;

}
