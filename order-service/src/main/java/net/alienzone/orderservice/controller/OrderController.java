package net.alienzone.orderservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.alienzone.orderservice.entity.Order;
import net.alienzone.orderservice.model.OrderRequest;
import net.alienzone.orderservice.model.OrderResponse;
import net.alienzone.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Log4j2
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest) {
        long orderId = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(orderId, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable("orderId") long orderId) {
        OrderResponse orderResponse = orderService.getOrderDetails(orderId);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }
}
