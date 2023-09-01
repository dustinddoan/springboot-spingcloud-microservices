package net.alienzone.orderservice.service;

import net.alienzone.orderservice.model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}
