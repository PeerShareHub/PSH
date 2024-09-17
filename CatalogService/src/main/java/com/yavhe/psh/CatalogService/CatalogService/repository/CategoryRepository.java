package com.yavhe.psh.CatalogService.CatalogService.repository;


import com.yavhe.psh.CatalogService.CatalogService.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Category findByName(String name);
}
