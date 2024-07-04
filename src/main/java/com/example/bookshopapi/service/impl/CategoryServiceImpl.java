package com.example.bookshopapi.service.impl;

import com.example.bookshopapi.entity.Category;
import com.example.bookshopapi.repository.CategoryRepository;
import com.example.bookshopapi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public List<Category> getAll(){
        return categoryRepository.findAll();
    }
}
