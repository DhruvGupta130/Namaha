package com.trulydesignfirm.namaha.controller;

import com.trulydesignfirm.namaha.dto.ProductDto;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Route only exposed to admin")
public class AdminController {

    private final ProductService productService;

    @PostMapping("/product/create")
    @Operation(summary = "Create a new product")
    public ResponseEntity<Response> createProduct(@RequestBody @Valid ProductDto dto) {
        Response response = productService.createProduct(dto);
        return new ResponseEntity<>(response, response.status());
    }

    @PutMapping("/product/update/{id}")
    @Operation(summary = "Update an existing product")
    public ResponseEntity<Response> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductDto dto) {
        Response response = productService.updateProduct(id, dto);
        return new ResponseEntity<>(response, response.status());
    }

    @DeleteMapping("product/delete/{id}")
    @Operation(summary = "Delete a product by ID")
    public ResponseEntity<Response> deleteProduct(@PathVariable Long id) {
        Response response = productService.deleteProduct(id);
        return new ResponseEntity<>(response, response.status());
    }
}
