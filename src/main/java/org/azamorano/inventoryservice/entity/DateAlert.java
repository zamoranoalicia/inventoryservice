package org.azamorano.inventoryservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table
public class DateAlert {

    ZonedDateTime expirationDate;

    ZonedDateTime alertDate;

    //TODO [Reverse Engineering] generate columns from DB
}