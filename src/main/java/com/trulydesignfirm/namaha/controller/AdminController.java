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

    @GetMapping("/product/variety")
    @Operation(summary = "Get all product varieties")
    public ResponseEntity<Response> getAllVarieties() {
        Response response = productService.getProductVarieties();
        return new ResponseEntity<>(response, response.status());
    }

    @PostMapping("/product/variety")
    @Operation(summary = "Create a new product variety")
    public ResponseEntity<Response> createVariety(@RequestParam("variety") String varietyName) {
        Response response = productService.createVariety(varietyName);
        return new ResponseEntity<>(response, response.status());
    }

    @DeleteMapping("/product/variety")
    @Operation(summary = "Delete a product variety")
    public ResponseEntity<Response> deleteVariety(@RequestParam("variety") String varietyName) {
        Response response = productService.deleteVariety(varietyName);
        return new ResponseEntity<>(response, response.status());
    }

    @GetMapping("/product/category")
    @Operation(summary = "Get all product categories")
    public ResponseEntity<Response> getAllCategories() {
        Response response = productService.getProductCategories();
        return new ResponseEntity<>(response, response.status());
    }

    @PostMapping("/product/category")
    @Operation(summary = "Create a new product category")
    public ResponseEntity<Response> createCategory(@RequestParam String category) {
        Response response = productService.createCategory(category);
        return new ResponseEntity<>(response, response.status());
    }

    @DeleteMapping("/product/category")
    @Operation(summary = "Delete a product category")
    public ResponseEntity<Response> deleteCategory(@RequestParam("category") String categoryName) {
        Response response = productService.deleteCategory(categoryName);
        return new ResponseEntity<>(response, response.status());
    }

    @GetMapping("/product/all")
    @Operation(summary = "Get all products")
    public ResponseEntity<Response> getAllProducts(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Response response = productService.getAllProducts(pageNumber, pageSize);
        return new ResponseEntity<>(response, response.status());
    }

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
