package net.alienzone.productservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.alienzone.productservice.entity.Product;
import net.alienzone.productservice.model.ProductRequest;
import net.alienzone.productservice.model.ProductResponse;
import net.alienzone.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
@Log4j2
public class ProductController {
    private ProductService productService;


//    @PreAuthorize("hasAuthority('Admin')")
    @PreAuthorize("hasAuthority('Admin') ||hasRole('Admin')")
    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest) {
        long productId = productService.addProduct(productRequest);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

//    @PreAuthorize("hasAuthority('Admin') || hasAuthority('Customer') || hasAuthority('SCOPE_interal')")
//    @PreAuthorize("hasAuthority('SCOPE_internal')")
    @PreAuthorize("hasRole('Admin') || hasRole('Customer') || hasAuthority('SCOPE_internal')")
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable long productId) {
        log.info("Get product by id: " + productId);
        ProductResponse productResponse = productService.getProductById(productId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('Admin') || hasAuthority('Customer') || hasAuthority('SCOPE_internal')")
    @PutMapping("/reduceQuantity/{productId}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable("productId") long productId, @RequestParam long quantity) {
        productService.reduceQuantity(productId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
