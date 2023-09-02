package net.alienzone.orderservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.alienzone.orderservice.entity.Order;
import net.alienzone.orderservice.external.client.ProductService;
import net.alienzone.orderservice.model.OrderRequest;
import net.alienzone.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final ProductService productService;

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

        return order.getId();
    }
}
