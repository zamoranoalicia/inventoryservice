package org.azamorano.inventoryservice.repository;

import org.azamorano.inventoryservice.entity.TherapeuticAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TherapeuticActionRepository extends JpaRepository<TherapeuticAction, UUID> {
}

