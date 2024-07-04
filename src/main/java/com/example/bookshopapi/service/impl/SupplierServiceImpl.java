package com.example.bookshopapi.service.impl;

import com.example.bookshopapi.entity.Supplier;
import com.example.bookshopapi.repository.SupplyRepository;
import com.example.bookshopapi.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplyRepository supplyRepository;
    @Override
    public List<Supplier> getAll() {
        return supplyRepository.findAll();
    }
}
