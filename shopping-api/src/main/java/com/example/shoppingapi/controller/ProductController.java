package com.example.shoppingapi.controller;

import com.example.shoppingapi.model.Product;
import com.example.shoppingapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<Product> all_products() {
        return productService.getAllProducts();
    }

    @PostMapping("/product")
    public void addNewProduct(@RequestBody Product product) throws Exception {
        if (product == null || product.getName() == null) {
            throw new Exception("Error: "+ HttpStatus.BAD_REQUEST);
        }
        product.setPurchaseDate(LocalDateTime.now());
        productService.addNewProduct(product);
    }

    @PutMapping("/product")
    public void updateProduct(Long id, String name, String storeName, String storeUrl) {
        Product product = new Product();
        product.setName(name);
        product.setStoreName(storeName);
        product.setStoreUrl(storeUrl);
        productService.updateProduct(product);
    }

    @DeleteMapping("/product")
    public void deleteProduct(Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/product/{id}")
    public Product getProduct(Long id) {
        return productService.getProduct(id);
    }
}
