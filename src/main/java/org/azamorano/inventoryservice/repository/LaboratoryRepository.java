package org.azamorano.inventoryservice.repository;

import org.azamorano.inventoryservice.entity.Laboratory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LaboratoryRepository extends JpaRepository<Laboratory, UUID> {
    Optional<Laboratory> getLaboratoriesByName(String name);
}
