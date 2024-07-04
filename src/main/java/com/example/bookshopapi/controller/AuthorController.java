package com.example.bookshopapi.controller;

import com.example.bookshopapi.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;
    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(authorService.getAll());
    }
}
