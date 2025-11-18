package com.example.lestock.controller;
import com.example.lestock.controller.dto.*;
import com.example.lestock.controller.mapper.MaterialTypeMapper;
import com.example.lestock.controller.mapper.StockMapper;
import com.example.lestock.controller.mapper.StockMovementMapper;
import com.example.lestock.model.MaterialType;
import com.example.lestock.model.Stock;
import com.example.lestock.model.StockMovement;
import com.example.lestock.service.MaterialTypeService;
import com.example.lestock.service.StockMovementService;
import com.example.lestock.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
@RestController
@RequestMapping("/material-types")
@RequiredArgsConstructor
@Tag(name = "Material Types")
public class MaterialTypeController implements GenericController{
    private final MaterialTypeMapper materialTypeMapper;
    private final MaterialTypeService materialTypeService;
    private final StockMapper stockMapper;
    private final StockService stockService;
    private final StockMovementMapper stockMovementMapper;
    private final StockMovementService stockMovementService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Save", description = "Register a new material type")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Material Type registered successfully"),
            @ApiResponse(responseCode = "422", description = "Validation Error"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "403", description = "User Role has no permission")
    })
    ResponseEntity<Void> saveMaterialType(@RequestBody @Valid MaterialTypeDTO materialTypeDTO) {
        MaterialType materialType = materialTypeMapper.toEntity(materialTypeDTO);
        materialTypeService.saveMaterialType(materialType);
        URI location = generateHeaderLocation(materialType.getId());
        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    @Operation(summary = "Get all Material Types", description = "Retrieve all Material Types from database")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returned all Material Types registered"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "403", description = "User Role has no permission")
    })
    ResponseEntity<List<MaterialTypeDTO>> getAllMaterialTypes() {
        List<MaterialTypeDTO> materialTypes = materialTypeService.getMaterialTypes()
                .stream()
                .map(materialTypeMapper::toDTO)
                .toList();
        return ResponseEntity.ok(materialTypes);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    @Operation(summary = "Get Material Type", description = "Retrieve a Material Type from database")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Material Type found"),
            @ApiResponse(responseCode = "404", description = "Material Type not found"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "403", description = "User Role has no permission")
    })
    ResponseEntity<MaterialTypeDTO> getMaterialType(@PathVariable Long id) {
        return materialTypeService.getMaterialType(id)
                .map(materialType -> {
                    MaterialTypeDTO materialTypeDTO = materialTypeMapper.toDTO(materialType);
                    return ResponseEntity.ok(materialTypeDTO);
                }).orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update Material Type", description = "Update a Material Type")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Material Type updated successfully"),
            @ApiResponse(responseCode = "404", description = "Material Type meant to be updated not found"),
            @ApiResponse(responseCode = "422", description = "Validation Error"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "403", description = "User Role has no permission")
    })
    ResponseEntity<Object> updateMaterialType(@RequestBody @Valid MaterialTypeDTO materialTypeDTO, @PathVariable Long id) {
        return materialTypeService.getMaterialType(id)
                .map(
                        materialType -> {
                            materialType.setName(materialTypeDTO.name());
                            materialType.setMetricUnit(materialTypeDTO.metricUnit());
                            materialType.setBrand(materialTypeDTO.brand());
                            materialTypeService.updateMaterialType(materialType);
                            return ResponseEntity.ok().build();
                        }
                ).orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Material Type", description = "Delete a Material Type")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Material Type deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Material Type meant to be deleted not found"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "403", description = "User Role has no permission")
    })
    ResponseEntity<Object> deleteMaterialType(@PathVariable Long id) {
        return materialTypeService.getMaterialType(id)
                .map(
                        materialType -> {
                            materialTypeService.deleteMaterialType(materialType);
                            return ResponseEntity.noContent().build();
                        }
                ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/{id}/stock")
    @Operation(summary = "Save", description = "Register a new stock for a specific material type")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Stock registered successfully"),
            @ApiResponse(responseCode = "422", description = "Validation Error"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "403", description = "User Role has no permission")
    })
    ResponseEntity<Object> saveStock(@PathVariable Long id, @RequestBody @Valid SaveStockDTO saveStockDTO) {
        return materialTypeService.getMaterialType(id)
                .map(materialType -> {
                    Stock stock = stockMapper.toEntity(saveStockDTO);
                    stock.setMaterialType(materialType);
                    stockService.saveStock(stock);
                    URI location = generateHeaderLocation(stock.getId());
                    return ResponseEntity.created(location).build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}/stock")
    @Operation(summary = "Get Stock", description = "Retrieve the Stock information of a specific Material Type")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stock found"),
            @ApiResponse(responseCode = "404", description = "Stock/Material type not found"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "403", description = "User Role has no permission")
    })
    ResponseEntity<GetStockDTO> getStock(@PathVariable Long id) {
        return materialTypeService.getMaterialType(id)
                .map(materialType -> {
                    return stockService.getStockByMaterialType(materialType)
                            .map(stock -> {
                                GetStockDTO stockDTO = stockMapper.toDTO(stock);
                                return ResponseEntity.ok(stockDTO);
                            }).orElseGet(() -> ResponseEntity.notFound().build());
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/stock")
    @Operation(summary = "Get all Stocks", description = "Retrieve stock information of all material types registered")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returned stock of all material types registered"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "403", description = "User Role has no permission")
    })
    ResponseEntity<List<GetStockDTO>> getAllStocks() {
        List<GetStockDTO> stockDTOS = stockService.getAllStocks()
                .stream()
                .map(stockMapper::toDTO)
                .toList();
        return ResponseEntity.ok(stockDTOS);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/{id}/stock-movement")
    @Operation(summary = "Save", description = "Register a new stock movement for a specific material type")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Stock movement registered successfully"),
            @ApiResponse(responseCode = "422", description = "Validation Error"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "403", description = "User Role has no permission")
    })
    ResponseEntity<Object> saveStockMovement(@PathVariable Long id, @RequestBody @Valid SaveStockMovementDTO saveStockMovementDTO) {
       return materialTypeService.getMaterialType(id)
                .map(materialType -> {
                    if(materialType.getStock() == null) {
                        SaveStockDTO stockDTO = new SaveStockDTO((float) 0, (float) 0);
                        saveStock(id, stockDTO);
                    }
                    StockMovement stockMovement = stockMovementMapper.toEntity(saveStockMovementDTO);
                    stockMovement.setStock(materialType.getStock());
                    stockMovementService.updateStock(stockMovement, materialType);
                    stockMovementService.save(stockMovement);
                    URI location = generateHeaderLocation(stockMovement.getId());
                    return ResponseEntity.created(location).build();
                } ).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/stock-movement")
    @Operation(summary = "Get all Stock Movements", description = "Retrieve stock movement information of all material types registered")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returned stock movements of all material types registered"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "403", description = "User Role has no permission")
    })
    ResponseEntity<List<GetStockMovementDTO>> getAllStockMovements() {
        List<GetStockMovementDTO> stockMovementDTOS = stockMovementService.getAllStockMovements()
                .stream()
                .map(stockMovementMapper::toDTO)
                .toList();
        return ResponseEntity.ok(stockMovementDTOS);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}/stock-movement")
    @Operation(summary = "Get Stock Movement", description = "Retrieve the Stock movements information of a specific Material Type")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stock movement found"),
            @ApiResponse(responseCode = "404", description = "Material type not found"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "403", description = "User Role has no permission")
    })
    ResponseEntity<List<GetStockMovementDTO>> getStockMovementsByMaterialType(@PathVariable Long id) {
        return materialTypeService.getMaterialType(id)
                .map(materialType -> {
                    List<GetStockMovementDTO> stockMovementDTOS = stockMovementService
                            .getAllStockMovementsByStock(materialType.getStock())
                            .stream()
                            .map(stockMovementMapper::toDTO)
                            .toList();
                    return ResponseEntity.ok(stockMovementDTOS);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{idMaterialType}/stock-movement/{idStockMovement}")
    @Operation(summary = "Update Stock Movement", description = "Adjust the information of a stock movement of a specific material type")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stock Movement updated successfully"),
            @ApiResponse(responseCode = "404", description = "Material Type not found"),
            @ApiResponse(responseCode = "422", description = "Validation Error"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "403", description = "User Role has no permission")
    })
    ResponseEntity<Object> adjustStockMovement(@PathVariable Long idMaterialType, @PathVariable Long idStockMovement, @RequestBody @Valid SaveStockMovementDTO saveStockMovementDTO) {
       return  materialTypeService.getMaterialType(idMaterialType)
                .map(materialType -> stockMovementService.getStockMovement(idStockMovement)
                        .map(stockMovement -> {
                            stockMovementService.undoStockMovement(stockMovement, materialType);
                            stockMovementMapper.updateStockMovement(stockMovement, saveStockMovementDTO);
                            stockMovementService.updateStock(stockMovement, materialType);
                            stockMovementService.save(stockMovement);
                            return ResponseEntity.ok().build();
                        }).orElseGet(() -> ResponseEntity.notFound().build()))
               .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
