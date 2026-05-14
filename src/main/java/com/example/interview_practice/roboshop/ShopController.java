package com.example.interview_practice.roboshop;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShopController {

    @GetMapping("/api/products")
    public ResponseEntity<List<Product>> products(){
       return ResponseEntity.ok(ProductFactory.availableProducts());
    }

}
