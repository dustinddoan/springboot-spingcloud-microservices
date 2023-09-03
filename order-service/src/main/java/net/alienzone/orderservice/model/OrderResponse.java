package net.alienzone.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private long orderId;
    private String status;
    private Instant orderDate;
    private long amount;
    private ProductDetails productDetails;
    private PaymentDetails paymentDetails;

    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class ProductDetails {
        private long productId;
        private String productName;
        private long price;
        private long quantity;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PaymentDetails {
        private long paymentId;
        private String status;
        private PaymentMethod paymentMethod;
        private long amount;
        private Instant paymentDate;
        private long orderId;

    }


}
