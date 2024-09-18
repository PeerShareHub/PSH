package com.yavhe.psh.CatalogService.CatalogService.repository;

import com.yavhe.psh.CatalogService.CatalogService.entity.Category;
import com.yavhe.psh.CatalogService.CatalogService.entity.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findByTitleContaining(String title, Sort sort);
    List<Post> findAll(Sort sort);
    List<Post> findByCategoryAndTitleContaining(Category category, String title, Sort sort);
}