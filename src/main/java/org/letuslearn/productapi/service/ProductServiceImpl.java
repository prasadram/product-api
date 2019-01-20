package org.letuslearn.productapi.service;

import org.letuslearn.productapi.model.Product;
import org.letuslearn.productapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

  private ProductRepository productRepository;

  @Autowired
  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Flux<Product> getAllProducts() {
    return productRepository.findAll();
  }

  @Override
  public Mono<Product> getProductById(String id) {
    return productRepository.findById(id);
  }

  @Override
  public Mono<Product> addProduct(Product product) {
    return productRepository.save(product);
  }

  @Override
  public Mono<Product> updateProduct(String id, Product product) {
    return productRepository.findById(id)
            .flatMap(existingProduct -> {
              existingProduct.setName(product.getName());
              existingProduct.setPrice(product.getPrice());
              return productRepository.save(existingProduct);
            });
  }

  @Override
  public Mono<Void> deleteProduct(String id) {
    return productRepository.findById(id)
            .flatMap(existingProduct -> productRepository.delete(existingProduct));
  }

  @Override
  public Mono<Void> deleteAllProducts() {
    return productRepository.deleteAll();
  }


}
