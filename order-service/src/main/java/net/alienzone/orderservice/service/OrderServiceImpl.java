package net.alienzone.orderservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.alienzone.orderservice.entity.Order;
import net.alienzone.orderservice.external.client.PaymentService;
import net.alienzone.orderservice.external.client.ProductService;
import net.alienzone.orderservice.external.response.PaymentResponse;
import net.alienzone.orderservice.external.response.ProductResponse;
import net.alienzone.orderservice.model.OrderRequest;
import net.alienzone.orderservice.model.OrderResponse;
import net.alienzone.orderservice.model.PaymentRequest;
import net.alienzone.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@AllArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final PaymentService paymentService;
    private final RestTemplate restTemplate;

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        log.info("Calling ProductService to reduce quantity: "+ orderRequest.getQuantity() + orderRequest.getProductId());
        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

        log.info("Placing order");
        Order order = Order.builder()
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .amount(orderRequest.getTotalAmount())
                .orderDate(Instant.now())
                .orderStatus("CREATED")
                .build();
        log.info("repo");
        order = orderRepository.save(order);
        log.info("Order placed successfully with ID: " + order.getId());


        log.info("Calling PaymentService to create payment");
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getId())
                .amount(orderRequest.getTotalAmount())
                .paymentMethod(orderRequest.getPaymentMethod())
                .build();

        String orderStatus = null;

        try {
            paymentService.doPayment(paymentRequest);
            log.info("Payment created successfully. Changed status to PLACED");
            orderStatus = "PLACED";
        } catch (Exception e) {
            log.error("Payment creation failed. Changed status to FAILED");
            orderStatus = "FAILED";
        }

        order.setOrderStatus(orderStatus);
        order = orderRepository.save(order);
        log.info("Order placed successfully with ID: " + order.getId());
        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // NOT using FeignClient to get the ProductDetails
        // using RestTemplate to get the ProductDetails and PaymentDetails
        ProductResponse productResponse = restTemplate.getForObject("http://PRODUCT-SERVICE/product/" + order.getProductId(), ProductResponse.class);

        log.info("produdctResponse " + productResponse);

        OrderResponse.ProductDetails productDetails = OrderResponse.ProductDetails.builder()
                .productId(productResponse.getProductId())
                .productName(productResponse.getProductName())
                .quantity(productResponse.getQuantity())
                .price(productResponse.getPrice())
                .build();

        log.info("Get PaymentDetails");

        PaymentResponse paymentResponse = restTemplate.getForObject("http://PAYMENT-SERVICE/payment/order/"+order.getId(), PaymentResponse.class);
        log.info("paymentResponse " + paymentResponse);

        OrderResponse.PaymentDetails paymentDetails = OrderResponse.PaymentDetails.builder()
                .orderId(paymentResponse.getOrderId())
                .status(paymentResponse.getStatus())
                .paymentMethod(paymentResponse.getPaymentMethod())
                .paymentDate(paymentResponse.getPaymentDate())
                .amount(paymentResponse.getAmount())
                .build();



        OrderResponse orderReponse = OrderResponse.builder()
                .orderId(order.getId())
                .status(order.getOrderStatus())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .productDetails(productDetails)
                .paymentDetails(paymentDetails)
                .build();

        return orderReponse;
    }
}
