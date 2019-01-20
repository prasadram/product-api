package org.letuslearn.productapi.controller;


import org.letuslearn.productapi.model.Product;
import org.letuslearn.productapi.model.ProductEvent;
import org.letuslearn.productapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/products")
public class ProductController {

  private ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public Flux<Product> getAllProducts() {
    return productService.getAllProducts();
  }

  @GetMapping("{id}")
  public Mono<ResponseEntity<Product>> getProduct(@PathVariable String id) {
    return productService.getProductById(id)
            .map(product -> ResponseEntity.ok(product))
            .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Product> saveProduct(@RequestBody Product product) {
    return productService.addProduct(product);
  }


  @PutMapping("{id}")
  public Mono<ResponseEntity<Product>> updateProduct(@PathVariable String id, @RequestBody Product product) {
    return productService.updateProduct(id, product)
            .map(updatedProduct -> ResponseEntity.ok(updatedProduct))
            .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  // 404 is not working need to check
  // how we can pass not found from service layer to controller
  @DeleteMapping("{id}")
  public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable String id) {
    return productService.deleteProduct(id).then(Mono.just(ResponseEntity.ok().<Void>build()))
              .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping
  public Mono<Void> deleteAllProducts() {
    return productService.deleteAllProducts();
  }

  @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<ProductEvent> getProductEvents() {
    return Flux.interval(Duration.ofSeconds(1))
            .map(val ->
              new ProductEvent(val, "Product Event")
            );
  }
}
