package com.example.bookshopapi.controller;

import com.example.bookshopapi.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;
    @GetMapping("all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(supplierService.getAll());
    }
}
