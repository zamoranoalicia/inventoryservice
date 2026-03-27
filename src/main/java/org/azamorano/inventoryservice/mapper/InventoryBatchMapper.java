package org.azamorano.inventoryservice.mapper;

import org.azamorano.inventoryservice.dto.request.InventoryBatchRequestDto;
import org.azamorano.inventoryservice.dto.response.InventoryBatchResponseDto;
import org.azamorano.inventoryservice.entity.InventoryBatch;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InventoryBatchMapper {

    public InventoryBatch toEntity(InventoryBatchRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }
        InventoryBatch batch = new InventoryBatch();
        batch.setLotNumber(requestDto.getLotNumber());
        batch.setReceptionDate(requestDto.getReceptionDate());
        batch.setExpirationDate(requestDto.getExpirationDate());
        batch.setQuantity(requestDto.getQuantity());
        batch.setAlertBeforeDays(requestDto.getAlertBeforeDays());
        batch.setPurchasePrice(requestDto.getPurchasePrice());
        return batch;
    }

    public InventoryBatchResponseDto toResponseDto(InventoryBatch entity) {
        if (entity == null) {
            return null;
        }
        InventoryBatchResponseDto responseDto = new InventoryBatchResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setProductId(entity.getProduct() != null ? entity.getProduct().getId() : null);
        responseDto.setSupplierId(entity.getSupplier() != null ? entity.getSupplier().getId() : null);
        responseDto.setLotNumber(entity.getLotNumber());
        responseDto.setReceptionDate(entity.getReceptionDate());
        responseDto.setExpirationDate(entity.getExpirationDate());
        responseDto.setQuantity(entity.getQuantity());
        responseDto.setAlertBeforeDays(entity.getAlertBeforeDays());
        responseDto.setPurchasePrice(entity.getPurchasePrice());
        return responseDto;
    }

    public List<InventoryBatchResponseDto> toResponseDtoList(List<InventoryBatch> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}

