package net.alienzone.productservice.service;

import net.alienzone.productservice.model.ProductRequest;
import net.alienzone.productservice.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);
}
