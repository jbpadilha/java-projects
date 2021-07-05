package com.example.shoppingapi.service;

import com.example.shoppingapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProductService {
    ObjectMapper mapper = new ObjectMapper();
    private List<Product> products = new LinkedList<>();
    public ProductService() {
        try {
            products = new LinkedList<Product>(Arrays.asList(mapper.readValue(Paths.get("products.json").toFile(), Product[].class)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public void deleteProduct(Long id) {
        products.removeIf(p -> p.getId().equals(id));
    }

    public void addNewProduct(Product product) {
        Long lastId = new Long(products.size() + 1);
        product.setId(lastId);
        products.add(product);

        // Write Json File
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(Paths.get("products.json").toFile(), products);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Product product) {
        int index = 0;
        for (Product product1 : products) {
            if (product1.getId().equals(product.getId()))  {
                product.setPurchaseDate(product1.getPurchaseDate());
                products.set(index, product);
            }
            index++;
        }
    }

    public Product getProduct(Long id) {
        Product foundProduct = null;
        for (Product product1 : products) {
            if (product1.getId().equals(id))  {
                foundProduct = product1;
                break;
            }
        }
        return foundProduct;
    }
}
