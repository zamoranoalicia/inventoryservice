package org.azamorano.inventoryservice.repository;

import org.azamorano.inventoryservice.entity.ProductSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductSupplierRepository extends JpaRepository<ProductSupplier, UUID> {
}

