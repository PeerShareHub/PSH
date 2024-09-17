package com.yavhe.psh.CatalogService.CatalogService.controller;

import com.yavhe.psh.CatalogService.CatalogService.dto.RentalItemDto;
import com.yavhe.psh.CatalogService.CatalogService.entity.Category;
import com.yavhe.psh.CatalogService.CatalogService.entity.RentalItem;
import com.yavhe.psh.CatalogService.CatalogService.service.CategoryService;
import com.yavhe.psh.CatalogService.CatalogService.service.RentalItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/items")
public class RentalItemController {

    @Autowired
    private final RentalItemService rentalItemService;
    @Autowired
    private CategoryService categoryService;

    public RentalItemController(RentalItemService rentalItemService) {
        this.rentalItemService = rentalItemService;
    }

    @GetMapping
    public List<RentalItem> getAllRentalItems() {
        return rentalItemService.getAllItems();
    }

    @GetMapping("/{id}")
    public RentalItem getRentalItemById(@PathVariable UUID id) {
        return rentalItemService.getItemById(id);
    }

    @PostMapping
    public ResponseEntity<RentalItem> createRentalItem(@Valid @RequestBody RentalItemDto rentalItemDto) {
        System.out.println("Received RentalItemDto: " + rentalItemDto);

        Category category = categoryService.findByName(rentalItemDto.getCategoryName());
        if (category == null) {
            System.out.println("Category not found: " + rentalItemDto.getCategoryName());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RentalItem rentalItem = new RentalItem();
        rentalItem.setTitle(rentalItemDto.getTitle());
        rentalItem.setDescription(rentalItemDto.getDescription());
        rentalItem.setPricePerDay(rentalItemDto.getPricePerDay());
        rentalItem.setLocation(rentalItemDto.getLocation());
        rentalItem.setCategory(category);

        RentalItem createdRentalItem = rentalItemService.addNewItem(rentalItem);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdRentalItem);
    }

    @DeleteMapping("/{id}")
    public void deleteRentalItem(@PathVariable UUID id) {
        rentalItemService.deleteItem(id);
    }
}