package com.yavhe.psh.CatalogService.CatalogService.controller;


import com.yavhe.psh.CatalogService.CatalogService.entity.Category;
import com.yavhe.psh.CatalogService.CatalogService.entity.Post;
import com.yavhe.psh.CatalogService.CatalogService.service.CategoryService;
import com.yavhe.psh.CatalogService.CatalogService.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    private final PostService postService;

    public CategoryController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable UUID id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable UUID id, @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{categoryId}/posts")
    public ResponseEntity<List<Post>> getPostsByCategory(
            @PathVariable UUID categoryId,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "sort", defaultValue = "createdAt,desc") String sort) {

        // Обрабатываем параметры сортировки
        String[] sortParams = sort.split(",");
        Sort sortOrder = Sort.by(Sort.Order.by(sortParams[0])
                .with(Sort.Direction.fromString(sortParams[1])));

        // Вызываем метод сервиса для поиска постов по категории с фильтрацией
        List<Post> posts = postService.getPostsByCategory(categoryId, minPrice, maxPrice, sortOrder);
        return ResponseEntity.ok(posts);
    }
}
