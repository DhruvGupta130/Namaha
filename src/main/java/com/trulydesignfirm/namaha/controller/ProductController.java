package com.trulydesignfirm.namaha.controller;

import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/store/flower")
    public ResponseEntity<Response> getAllFlowers(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Response response = productService.getFlowerStore(pageNumber, pageSize);
        return new ResponseEntity<>(response, response.status());
    }

    @GetMapping("/store/pooja")
    public ResponseEntity<Response> getAllPoojaItems(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Response response = productService.getPoojaStore(pageNumber, pageSize);
        return new ResponseEntity<>(response, response.status());
    }
}
