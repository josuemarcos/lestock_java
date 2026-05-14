package com.example.lestock.controller;
import com.example.lestock.controller.common.GenericController;
import com.example.lestock.controller.dto.stock.*;
import com.example.lestock.controller.mapper.stock.MaterialTypeMapper;
import com.example.lestock.controller.mapper.stock.StockMapper;
import com.example.lestock.controller.mapper.stock.StockMovementMapper;
import com.example.lestock.model.User;
import com.example.lestock.model.stock.MaterialType;
import com.example.lestock.model.stock.Stock;
import com.example.lestock.model.stock.StockMovement;
import com.example.lestock.security.annotation.LoggedUser;
import com.example.lestock.service.materialtype.MaterialTypeService;
import com.example.lestock.service.stockmovement.StockMovementService;
import com.example.lestock.service.stock.StockService;
import io.swagger.v3.oas.annotations.Operation;
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
public class MaterialTypeController implements GenericController {
    private final MaterialTypeMapper materialTypeMapper;
    private final MaterialTypeService materialTypeService;
    private final StockMapper stockMapper;
    private final StockService stockService;
    private final StockMovementMapper stockMovementMapper;
    private final StockMovementService stockMovementService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Save Material Type", description = "Register a new material type")
    ResponseEntity<Void> saveMaterialType(@RequestBody @Valid SaveMaterialTypeDTO materialTypeDTO,
                                          @LoggedUser User loggedUser) {
        MaterialType savedMaterialType = materialTypeService
                .saveMaterialType(materialTypeMapper.toEntity(materialTypeDTO), loggedUser);
        URI location = generateHeaderLocation(savedMaterialType.getId());
        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    @Operation(summary = "Get all Material Types", description = "Retrieve all Material Types from database")
    ResponseEntity<List<GetMaterialTypeDTO>> getAllMaterialTypes() {
        List<GetMaterialTypeDTO> materialTypes = materialTypeService.getMaterialTypes()
                .stream()
                .map(materialTypeMapper::toDTO)
                .toList();
        return ResponseEntity.ok(materialTypes);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{materialTypeId}")
    @Operation(summary = "Get Material Type", description = "Retrieve a Material Type from database")
    ResponseEntity<GetMaterialTypeDTO> getMaterialType(@PathVariable Long materialTypeId) {
        return ResponseEntity.ok(materialTypeMapper.toDTO(materialTypeService.getMaterialType(materialTypeId)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{materialTypeId}")
    @Operation(summary = "Update Material Type", description = "Update a Material Type")
    ResponseEntity<Object> updateMaterialType(@RequestBody @Valid SaveMaterialTypeDTO materialTypeDTO,
                                              @PathVariable Long materialTypeId,
                                              @LoggedUser User loggedUser) {
        materialTypeService.updateMaterialType(materialTypeId, materialTypeDTO, loggedUser );
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{materialTypeId}")
    @Operation(summary = "Delete Material Type", description = "Delete a Material Type")
    ResponseEntity<Object> deleteMaterialType(@PathVariable Long materialTypeId) {
        materialTypeService.deleteMaterialType(materialTypeId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/{materialTypeId}/stock")
    @Operation(summary = "Save Stock", description = "Register a new stock for a specific material type")
    ResponseEntity<Object> saveStock(@PathVariable Long materialTypeId, @RequestBody @Valid SaveStockDTO saveStockDTO,
                                     @LoggedUser User loggedUser) {
        Stock savedStock = stockService.saveStock(materialTypeId, stockMapper.toEntity(saveStockDTO), loggedUser);
        URI location = generateHeaderLocation(savedStock.getId());
        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{materialTypeId}/stock")
    @Operation(summary = "Get Stock", description = "Retrieve the Stock information of a specific Material Type")
    ResponseEntity<GetStockDTO> getStock(@PathVariable Long materialTypeId) {
        return ResponseEntity.ok(stockMapper.toDTO(stockService.getStockByMaterialType(materialTypeId)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/stock")
    @Operation(summary = "Get all Stocks", description = "Retrieve stock information of all material types registered")
    ResponseEntity<List<GetStockDTO>> getAllStocks() {
        List<GetStockDTO> stockDTOS = stockService.getAllStocks()
                .stream()
                .map(stockMapper::toDTO)
                .toList();
        return ResponseEntity.ok(stockDTOS);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/{materialTypeId}/stock-movement")
    @Operation(summary = "Save Stock Movement", description = "Register a new stock movement for a specific material type")
    ResponseEntity<Object> saveStockMovement(@PathVariable Long materialTypeId, @RequestBody @Valid SaveStockMovementDTO saveStockMovementDTO,
                                             @LoggedUser User loggedUser) {
       StockMovement savedStockMovement = stockMovementService.save(materialTypeId,
               saveStockMovementDTO,
               loggedUser);
       URI location = generateHeaderLocation(savedStockMovement.getId());
       return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/stock-movement")
    @Operation(summary = "Get all Stock Movements", description = "Retrieve stock movement information of all material types registered")
    ResponseEntity<List<GetStockMovementDTO>> getAllStockMovements() {
        List<GetStockMovementDTO> stockMovementDTOS = stockMovementService.getAllStockMovements()
                .stream()
                .map(stockMovementMapper::toDTO)
                .toList();
        return ResponseEntity.ok(stockMovementDTOS);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{materialTypeId}/stock-movement")
    @Operation(summary = "Get Stock Movement", description = "Retrieve the Stock movements information of a specific Material Type")
    ResponseEntity<List<GetStockMovementDTO>> getStockMovementsByMaterialType(@PathVariable Long materialTypeId) {
        List<GetStockMovementDTO> stockMovements = stockMovementService
                .getAllStockMovementsByMaterialType(materialTypeId)
                .stream()
                .map(stockMovementMapper::toDTO)
                .toList();
        return ResponseEntity.ok(stockMovements);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{materialTypeId}/stock-movement/{stockMovementId}")
    @Operation(summary = "Update Stock Movement", description = "Adjust the information of a stock movement of a specific material type")
    ResponseEntity<Object> adjustStockMovement(@PathVariable Long materialTypeId,
                                               @PathVariable Long stockMovementId,
                                               @RequestBody @Valid SaveStockMovementDTO newStockMovementDTO,
                                               @LoggedUser User loggedUser) {
        stockMovementService.updateStockMovement(materialTypeId, stockMovementId, newStockMovementDTO, loggedUser);
        return ResponseEntity.ok().build();
    }


}
