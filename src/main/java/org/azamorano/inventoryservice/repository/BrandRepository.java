package org.azamorano.inventoryservice.repository;

import org.azamorano.inventoryservice.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {
    Optional<Brand> getBrandByName(String name);
}
