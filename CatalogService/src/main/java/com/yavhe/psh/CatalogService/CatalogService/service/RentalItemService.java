package com.yavhe.psh.CatalogService.CatalogService.service;

import com.yavhe.psh.CatalogService.CatalogService.entity.RentalItem;
import com.yavhe.psh.CatalogService.CatalogService.repository.RentalItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RentalItemService {
    private final RentalItemRepository rentalItemRepository;

    public RentalItemService(RentalItemRepository rentalItemRepository) {
        this.rentalItemRepository = rentalItemRepository;
    }

    public List<RentalItem> getAllItems() {
        return rentalItemRepository.findAll();
    }

    public RentalItem getItemById(UUID id) {
        return rentalItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
    }

    public RentalItem addNewItem(RentalItem rentalItem) {
        return rentalItemRepository.save(rentalItem);
    }

    public void deleteItem(UUID id) {
        rentalItemRepository.deleteById(id);
    }
}
