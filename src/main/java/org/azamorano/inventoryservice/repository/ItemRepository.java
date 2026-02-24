package org.azamorano.inventoryservice.repository;

import org.azamorano.inventoryservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<UUID, Product> {
}
