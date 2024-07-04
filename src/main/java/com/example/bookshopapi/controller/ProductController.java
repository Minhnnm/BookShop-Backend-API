package com.example.bookshopapi.controller;

import com.example.bookshopapi.dto.product.ProductRequestDto;
import com.example.bookshopapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                                           @RequestParam(name = "sort_by", required = false, defaultValue = "created_date") String sortBy,
                                           @RequestParam(name = "sort_dir", required = false, defaultValue = "asc") String sortDir,
                                           @RequestParam(name = "category_id", required = false) Integer categoryId,
                                           @RequestParam(name = "supply_id", required = false) Integer supplyId,
                                           @RequestParam(name = "author_id", required = false) Integer authorId) {
        return ResponseEntity.ok(productService.searchProduct(page, limit, queryString, sortBy, sortDir, categoryId, authorId, supplyId));
    }
    @GetMapping("/incategory/{categoryId}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable Integer categoryId) {
       return ResponseEntity.ok(productService.findByCategory(categoryId));
    }
    @GetMapping("/author/{authorId}")
    public ResponseEntity<?> getProductsByAuthor(@PathVariable Integer authorId) {
        return ResponseEntity.ok(productService.findByAuthor(authorId));
    }
    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<?> getProductsBySupplier(@PathVariable Integer supplierId) {
        return ResponseEntity.ok(productService.findBySupplier(supplierId));
    }
    @GetMapping("findById")
    public ResponseEntity<?> findById(@RequestParam("id") Integer id){
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody ProductRequestDto product){
        return ResponseEntity.ok(productService.create(product));
    }
}
