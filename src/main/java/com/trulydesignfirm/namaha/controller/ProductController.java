package com.trulydesignfirm.namaha.controller;

import com.trulydesignfirm.namaha.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/store/flower")
    public void getAllProducts() {
        productService.getAllProducts();
    }

    @GetMapping("/store/pooja")
    public ResponseEntity<String> getAllPooja() {
        return ResponseEntity.ok("pooja");
    }
}
