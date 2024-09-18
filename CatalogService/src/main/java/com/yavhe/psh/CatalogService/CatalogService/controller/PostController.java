package com.yavhe.psh.CatalogService.CatalogService.controller;

import com.yavhe.psh.CatalogService.CatalogService.entity.Post;
import com.yavhe.psh.CatalogService.CatalogService.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/search")
    public ResponseEntity<List<Post>> searchPosts(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "category", required = false) String categoryName,
            @RequestParam(value = "sort", defaultValue = "date,desc") String sort) {

        String[] sortParams = sort.split(",");
        Sort sortOrder = Sort.by(Sort.Order.by(sortParams[0])
                .with(Sort.Direction.fromString(sortParams[1])));

        List<Post> posts = postService.searchPosts(title, categoryName, sortOrder);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable UUID id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post createdPost = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable UUID id, @RequestBody Post post) {
        Post updatedPost = postService.updatePost(id, post);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(
            @RequestParam(value = "sort", defaultValue = "createdAt,desc") String sort) {

        String[] sortParams = sort.split(",");
        Sort sortOrder;
        try {
            sortOrder = Sort.by(Sort.Order.by(sortParams[0])
                    .with(Sort.Direction.fromString(sortParams[1])));
        } catch (IllegalArgumentException e) {
            sortOrder = Sort.by(Sort.Order.desc("createdAt"));
        }

        List<Post> posts = postService.getAllPosts(sortOrder);
        return ResponseEntity.ok(posts);
    }

}
