package org.azamorano.inventoryservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class Brand {

    String brandId;

    String manufactorerId;
    //TODO [Reverse Engineering] generate columns from DB
}