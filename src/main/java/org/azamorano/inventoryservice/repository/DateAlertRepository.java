package org.azamorano.inventoryservice.repository;

import org.azamorano.inventoryservice.entity.DateAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DateAlertRepository extends JpaRepository<DateAlert, UUID> {
}

