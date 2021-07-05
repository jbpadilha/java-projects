package com.example.shoppingapi;

import com.example.shoppingapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootApplication(scanBasePackages={"com.example.shoppingapi"})
public class ShoppingApiApplication {

	public static void main(String[] args) {
		writeFirtJsonFile();
		SpringApplication.run(ShoppingApiApplication.class, args);
	}

	public static void writeFirtJsonFile(){
		try {
			List<Product> products = new LinkedList<>(Arrays.asList(
					new Product(1L, "Product 1", LocalDateTime.now(), "Store 1", "http://www.google.ca"),
					new Product(2L, "Product 2", LocalDateTime.now(), "Store 2", "http://www.google.com"),
					new Product(3L, "Product 3", LocalDateTime.now(), "Store 3", "http://www.google.au")
			));

			// create object mapper instance
			ObjectMapper mapper = new ObjectMapper();
			// convert map to JSON file
			mapper.writeValue(Paths.get("products.json").toFile(), products);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:3000");
			}
		};
	}

}
