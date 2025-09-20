package com.trulydesignfirm.namaha.controller;

import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/public")
@RestController
@RequiredArgsConstructor
public class PublicController {

    private final ProductService productService;

    @GetMapping("/product/variety")
    @Operation(summary = "Get all product varieties")
    public ResponseEntity<Response> getAllVarieties() {
        Response response = productService.getProductVarieties();
        return new ResponseEntity<>(response, response.status());
    }

    @GetMapping("/product/category")
    @Operation(summary = "Get all product categories")
    public ResponseEntity<Response> getAllCategories() {
        Response response = productService.getProductCategories();
        return new ResponseEntity<>(response, response.status());
    }
}
