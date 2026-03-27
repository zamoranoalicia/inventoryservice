package org.azamorano.inventoryservice.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.azamorano.inventoryservice.entity.Brand;
import org.azamorano.inventoryservice.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@Transactional
@Slf4j
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Brand saveBrand(Brand brand) {
        if (ofNullable(brand).isEmpty()) {
            throw new IllegalArgumentException("Brand should be present");
        }
        Optional<Brand> existingBrand = brandRepository.getBrandByName(brand.getName());

        if (existingBrand.isEmpty()) {
            Brand newBrand = new Brand(brand.getName());
            existingBrand = Optional.of(brandRepository.save(newBrand));
        }
        return existingBrand.get();
    }
}
