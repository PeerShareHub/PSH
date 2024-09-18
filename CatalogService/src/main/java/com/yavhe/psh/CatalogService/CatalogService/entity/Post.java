package com.yavhe.psh.CatalogService.CatalogService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID postId;
    private String title;
    private String description;
    private double pricePerDay;
    private LocalDateTime createdAt;
    private String imageUrl;
    private String location;
    private boolean available;
    private String authorId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
