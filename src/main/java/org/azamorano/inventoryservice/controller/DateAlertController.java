package org.azamorano.inventoryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.azamorano.inventoryservice.dto.request.DateAlertRequestDto;
import org.azamorano.inventoryservice.dto.response.DateAlertResponseDto;
import org.azamorano.inventoryservice.mapper.DateAlertMapper;
import org.azamorano.inventoryservice.service.DateAlertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/date-alerts")
@Tag(name = "Date Alerts", description = "APIs for managing date alerts")
public class DateAlertController {

    private final DateAlertService service;
    private final DateAlertMapper mapper;

    public DateAlertController(DateAlertService service, DateAlertMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create a new date alert")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Date alert created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    public ResponseEntity<DateAlertResponseDto> create(@Valid @RequestBody DateAlertRequestDto requestDto) {
        var entity = mapper.toEntity(requestDto);
        var created = service.create(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDto(created));
    }

    @GetMapping
    @Operation(summary = "Get all date alerts")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    public ResponseEntity<List<DateAlertResponseDto>> getAll() {
        var alerts = service.getAll();
        return ResponseEntity.ok(mapper.toResponseDtoList(alerts));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get date alert by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Date alert found"),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public ResponseEntity<DateAlertResponseDto> getById(@PathVariable UUID id) {
        var alert = service.getById(id);
        return alert.map(e -> ResponseEntity.ok(mapper.toResponseDto(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a date alert")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updated successfully"),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public ResponseEntity<DateAlertResponseDto> update(@PathVariable UUID id, @Valid @RequestBody DateAlertRequestDto requestDto) {
        try {
            var entity = mapper.toEntity(requestDto);
            var updated = service.update(id, entity);
            return ResponseEntity.ok(mapper.toResponseDto(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a date alert")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

