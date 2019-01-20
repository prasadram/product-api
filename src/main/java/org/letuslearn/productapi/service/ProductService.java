package org.letuslearn.productapi.service;

import org.letuslearn.productapi.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

  Flux<Product> getAllProducts();

  Mono<Product> getProductById(String id);

  Mono<Product> addProduct(Product product);

  Mono<Product> updateProduct(String id, Product product);

  Mono<Void> deleteProduct(String id);

  Mono<Void> deleteAllProducts();
}
