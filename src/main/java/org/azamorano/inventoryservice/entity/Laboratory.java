package org.azamorano.inventoryservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "laboratories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Laboratory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @Column(nullable = false)
    private String name;
    
    private String country;

    public Laboratory(String name, String country) {
        this.name = name;
        this.country = country;
    }
}