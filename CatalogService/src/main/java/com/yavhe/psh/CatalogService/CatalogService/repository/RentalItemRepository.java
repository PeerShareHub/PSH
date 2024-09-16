package com.yavhe.psh.CatalogService.CatalogService.repository;

import com.yavhe.psh.CatalogService.CatalogService.entity.RentalItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RentalItemRepository extends JpaRepository<RentalItem, UUID> {
}
