package com.yavhe.psh.CatalogService.CatalogService.controller;

import com.yavhe.psh.CatalogService.CatalogService.entity.RentalItem;
import com.yavhe.psh.CatalogService.CatalogService.service.RentalItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rental-items")
public class RentalItemController {

    private final RentalItemService rentalItemService;

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
    public RentalItem createRentalItem(@RequestBody RentalItem rentalItem) {
        return rentalItemService.addNewItem(rentalItem);
    }

    @DeleteMapping("/{id}")
    public void deleteRentalItem(@PathVariable UUID id) {
        rentalItemService.deleteItem(id);
    }
}