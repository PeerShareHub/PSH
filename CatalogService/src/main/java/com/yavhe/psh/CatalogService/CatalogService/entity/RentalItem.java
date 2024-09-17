package com.yavhe.psh.CatalogService.CatalogService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RentalItem {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot be longer than 100 characters")
    @Column(nullable = false)
    private String title;

    @Size(max = 500, message = "Description cannot be longer than 500 characters")
    private String description;

    @NotNull(message = "Price per day is required")
    @Min(value = 0, message = "Price per day must be at least 0")
    @Column(nullable = false)
    private Double pricePerDay;

    @NotBlank(message = "Location is required")
    @Size(max = 200, message = "Location cannot be longer than 200 characters")
    @Column(nullable = false)
    private String location;

    @NotNull(message = "Availability is required")
    @Column(nullable = false)
    private Boolean available = true;

    @Min(value = 0, message = "Rating must be at least 0")
    @Max(value = 10, message = "Rating cannot be more than 10")
    @Column(nullable = false)
    private double rating = 0.0;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
