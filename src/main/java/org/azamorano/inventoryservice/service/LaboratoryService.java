package org.azamorano.inventoryservice.service;

import org.azamorano.inventoryservice.entity.Laboratory;
import org.azamorano.inventoryservice.repository.LaboratoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class LaboratoryService {
    private final LaboratoryRepository laboratoryRepository;

    public LaboratoryService(LaboratoryRepository laboratoryRepository) {
        this.laboratoryRepository = laboratoryRepository;
    }

    public Laboratory saveLaboratory(Laboratory laboratory) {
        if (ofNullable(laboratory).isEmpty()) {
            throw new IllegalArgumentException("Laboratory must be defined");
        }
        Optional<Laboratory> existingLaboratory = laboratoryRepository.getLaboratoriesByName(laboratory.getName());

        if(existingLaboratory.isEmpty()) {
            existingLaboratory = Optional.of(laboratoryRepository.save(new Laboratory(laboratory.getName(),
                    laboratory.getCountry())));
        }

        return existingLaboratory.get();
    }

    public Laboratory getLaboratoryByName(String name) {
        return laboratoryRepository.getLaboratoriesByName(name).orElse(null);
    }
}
