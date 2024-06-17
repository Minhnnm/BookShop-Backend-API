package com.example.bookshopapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok("sdfdsf");
    }
}
