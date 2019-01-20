package org.letuslearn.productapi;

import org.letuslearn.productapi.model.Product;
import org.letuslearn.productapi.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ProductApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApiApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ReactiveMongoOperations mongoOperations, ProductRepository productRepository) {
		return args -> {
			Flux<Product> productFlux = Flux.just(
							new Product(null, "Big Latte", 2.58),
							new Product(null, "Big Decaf", 3.65),
							new Product(null, "Green Tea", 5.99))
							.flatMap(productRepository::save);
			productFlux.thenMany(productRepository.findAll())
			.subscribe(System.out::println);

			/*mongoOperations.collectionExists(Product.class)
							.flatMap(exists -> exists ? mongoOperations.dropCollection(Product.class) : Mono.just(exists))
							.thenMany(v -> mongoOperations.createCollection(Product.class))
							.thenMany(productFlux)
							.thenMany(productRepository.findAll())
							.subscribe(System.out::println);*/
		};



	}
}

