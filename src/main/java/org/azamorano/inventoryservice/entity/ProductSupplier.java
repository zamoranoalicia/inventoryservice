package org.azamorano.inventoryservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "product_suppliers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSupplier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "tax_id")
    private String taxId;

    @Column
    private String address;

    @Column
    private String phone;

    @Column
    private String email;

    @Column(name = "sanitary_authorization_number")
    private String sanitaryAuthorizationNumber;
}
