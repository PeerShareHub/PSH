package com.yavhe.psh.CatalogService.CatalogService.service;

import com.yavhe.psh.CatalogService.CatalogService.entity.Category;
import com.yavhe.psh.CatalogService.CatalogService.entity.Post;
import com.yavhe.psh.CatalogService.CatalogService.repository.CategoryRepository;
import com.yavhe.psh.CatalogService.CatalogService.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Post> searchPosts(String title, String categoryName, Sort sort) {
        if (categoryName == null) {
            return postRepository.findByTitleContaining(title, sort);
        } else {
            Category category = categoryRepository.findByName(categoryName)
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
            return postRepository.findByCategoryAndTitleContaining(category, title, sort);
        }
    }

    public List<Post> getAllPosts(Sort sort) {
        return postRepository.findAll(sort);
    }

    public Post getPostById(UUID id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
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
