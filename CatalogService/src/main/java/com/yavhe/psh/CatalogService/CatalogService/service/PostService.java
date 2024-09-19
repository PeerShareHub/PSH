package com.yavhe.psh.CatalogService.CatalogService.service;

import com.yavhe.psh.CatalogService.CatalogService.entity.Category;
import com.yavhe.psh.CatalogService.CatalogService.entity.Post;
import com.yavhe.psh.CatalogService.CatalogService.repository.CategoryRepository;
import com.yavhe.psh.CatalogService.CatalogService.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Post> searchPosts(String title, String categoryName, Pageable pageable) {
        Specification<Post> spec = (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();

            // Фильтрация по заголовку
            if (title != null) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.like(root.get("title"), "%" + title + "%"));
            }

            // Фильтрация по категории
            if (categoryName != null) {
                Category category = categoryRepository.findByName(categoryName)
                        .orElseThrow(() -> new EntityNotFoundException("Category not found"));
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("category").get("categoryId"), category.getCategoryId()));
            }

            return predicates;
        };

        return postRepository.findAll(spec, pageable);
    }

    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Post getPostById(UUID id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
    }

    public Page<Post> getPostsByCategory(UUID categoryId, Double minPrice, Double maxPrice, Pageable pageable) {
        Specification<Post> spec = (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();

            // Фильтрация по категории
            predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("category").get("categoryId"), categoryId));

            // Фильтрация по минимальной цене
            if (minPrice != null) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.greaterThanOrEqualTo(root.get("pricePerDay"), minPrice));
            }

            // Фильтрация по максимальной цене
            if (maxPrice != null) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.lessThanOrEqualTo(root.get("pricePerDay"), maxPrice));
            }

            return predicates;
        };

        return postRepository.findAll(spec, pageable);
    }


    public Post createPost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        if (post.getCategory() != null) {
            categoryRepository.findById(post.getCategory().getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        }
        return postRepository.save(post);
    }

    public Post updatePost(UUID id, Post post) {
        if (!postRepository.existsById(id)) {
            throw new EntityNotFoundException("Post not found");
        }
        if (post.getCategory() != null) {
            categoryRepository.findById(post.getCategory().getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        }
        post.setPostId(id);
        return postRepository.save(post);
    }

    public void deletePost(UUID id) {
        if (!postRepository.existsById(id)) {
            throw new EntityNotFoundException("Post not found");
        }
        postRepository.deleteById(id);
    }
}
