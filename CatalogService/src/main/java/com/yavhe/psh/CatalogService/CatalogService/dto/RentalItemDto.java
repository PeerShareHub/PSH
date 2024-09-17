package com.yavhe.psh.CatalogService.CatalogService.dto;


import com.yavhe.psh.CatalogService.CatalogService.entity.RentalItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
public class RentalItemDto {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price per day is required")
    private Double pricePerDay;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Category name is required")
    private String categoryName;  // Должно совпадать с полем в JSON
}

