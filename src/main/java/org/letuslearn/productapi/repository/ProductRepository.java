package org.letuslearn.productapi.repository;

import org.letuslearn.productapi.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {


}
