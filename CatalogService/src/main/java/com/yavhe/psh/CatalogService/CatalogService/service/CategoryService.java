package com.yavhe.psh.CatalogService.CatalogService.service;

import com.yavhe.psh.CatalogService.CatalogService.entity.Category;
import com.yavhe.psh.CatalogService.CatalogService.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public Category save(Category category) {
        return categoryRepository.save(category);
    }


    public List<Category> findAll() {
        return categoryRepository.findAll();
    }


    public Category findById(UUID id) {
        return categoryRepository.findById(id).orElse(null);
    }


    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }
}