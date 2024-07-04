package com.example.bookshopapi.service.impl;

import com.example.bookshopapi.entity.Author;
import com.example.bookshopapi.repository.AuthorRepository;
import com.example.bookshopapi.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }
}
