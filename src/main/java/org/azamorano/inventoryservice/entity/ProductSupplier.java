package org.azamorano.inventoryservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class ProductSupplier {

    @Id
    @GeneratedValue
    UUID id;

    String name;

    String taxId;

    String address;

    String phone;

    String email;

    String sanitaryAuthorizationNumber;

}
