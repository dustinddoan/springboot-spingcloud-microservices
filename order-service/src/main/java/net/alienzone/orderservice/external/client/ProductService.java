package net.alienzone.orderservice.external.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import net.alienzone.orderservice.exception.CustomException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CircuitBreaker(name="external", fallbackMethod = "fallback")
@FeignClient(name = "PRODUCT-SERVICE/product")
public interface ProductService {
    @PutMapping("/reduceQuantity/{productId}")
    ResponseEntity<Void> reduceQuantity(
            @PathVariable("productId") long productId,
            @RequestParam long quantity);

    default void fallback(Exception exception) {
        throw new CustomException(
                "ProductService is down. Please try again later.",
                "SERVICE_UNAVAILABLE",
                500);
    }

}
