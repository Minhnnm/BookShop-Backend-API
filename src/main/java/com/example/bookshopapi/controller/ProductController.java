package com.example.bookshopapi.controller;

import com.example.bookshopapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<?> searchProduct(@RequestParam(name = "limit", defaultValue = "10") int limit,
                                           @RequestParam(name = "page", defaultValue = "1") int page,
                                           @RequestParam(name = "query_string", defaultValue = "") String queryString,
                                           @RequestParam(name = "categoryId", required = false) int categoryId,
                                           @RequestParam(name = "supplyId", required = false) int supplyId,
                                           @RequestParam(name = "authorId", required = false) int authorId) {
        return ResponseEntity.ok(productService.searchProduct(page, limit, queryString, categoryId, authorId, supplyId));
    }
}
