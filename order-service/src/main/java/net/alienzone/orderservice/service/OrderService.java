package net.alienzone.orderservice.service;

import net.alienzone.orderservice.model.OrderRequest;
import net.alienzone.orderservice.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
