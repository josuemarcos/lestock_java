package com.example.lestock.controller.stock;
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
import com.example.lestock.service.stock.MaterialTypeService;
import com.example.lestock.service.stock.StockMovementService;
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
        MaterialType materialType = materialTypeMapper.toEntity(materialTypeDTO);
        materialTypeService.saveMaterialType(materialType, loggedUser);
        URI location = generateHeaderLocation(materialType.getId());
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
    @GetMapping("/{id}")
    @Operation(summary = "Get Material Type", description = "Retrieve a Material Type from database")
    ResponseEntity<GetMaterialTypeDTO> getMaterialType(@PathVariable Long id) {
        return materialTypeService.getMaterialType(id)
                .map(materialType -> {
                    GetMaterialTypeDTO materialTypeDTO = materialTypeMapper.toDTO(materialType);
                    return ResponseEntity.ok(materialTypeDTO);
                }).orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update Material Type", description = "Update a Material Type")
    ResponseEntity<Object> updateMaterialType(@RequestBody @Valid GetMaterialTypeDTO materialTypeDTO,
                                              @PathVariable Long id,
                                              @LoggedUser User loggedUser) {
        return materialTypeService.getMaterialType(id)
                .map(
                        materialType -> {
                            materialType.setName(materialTypeDTO.name());
                            materialType.setMetricUnit(materialTypeDTO.metricUnit());
                            materialType.setBrand(materialTypeDTO.brand());
                            materialTypeService.updateMaterialType(materialType, loggedUser);
                            return ResponseEntity.ok().build();
                        }
                ).orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Material Type", description = "Delete a Material Type")
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
    @Operation(summary = "Save Stock", description = "Register a new stock for a specific material type")
    ResponseEntity<Object> saveStock(@PathVariable Long id, @RequestBody @Valid SaveStockDTO saveStockDTO,
                                     @LoggedUser User loggedUser) {
        return materialTypeService.getMaterialType(id)
                .map(materialType -> {
                    Stock stock = stockMapper.toEntity(saveStockDTO);
                    stock.setMaterialType(materialType);
                    stockService.saveStock(stock, loggedUser);
                    URI location = generateHeaderLocation(stock.getId());
                    return ResponseEntity.created(location).build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}/stock")
    @Operation(summary = "Get Stock", description = "Retrieve the Stock information of a specific Material Type")
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
       return materialTypeService.getMaterialType(materialTypeId)
                .map(materialType -> {
                    if(materialType.getStock() == null) {
                        SaveStockDTO stockDTO = new SaveStockDTO((float) 0, (float) 0);
                        saveStock(materialTypeId, stockDTO, loggedUser);
                    }
                    StockMovement stockMovement = stockMovementMapper.toEntity(saveStockMovementDTO);
                    stockMovementService.save(stockMovement, materialType, loggedUser);
                    URI location = generateHeaderLocation(stockMovement.getId());
                    return ResponseEntity.created(location).build();
                } ).orElseGet(() -> ResponseEntity.notFound().build());

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
    @GetMapping("/{id}/stock-movement")
    @Operation(summary = "Get Stock Movement", description = "Retrieve the Stock movements information of a specific Material Type")
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
    ResponseEntity<Object> adjustStockMovement(@PathVariable Long idMaterialType,
                                               @PathVariable Long idStockMovement,
                                               @RequestBody @Valid SaveStockMovementDTO newStockMovementDTO,
                                               @LoggedUser User loggedUser) {
       return  materialTypeService.getMaterialType(idMaterialType)
                .map(materialType -> stockMovementService.getStockMovement(idStockMovement)
                        .map(stockMovement -> {
                            StockMovement updatedStockMovement = stockMovementMapper.toEntity(newStockMovementDTO);
                            stockMovementService.updateStock(updatedStockMovement, loggedUser);
                            return ResponseEntity.ok().build();
                        }).orElseGet(() -> ResponseEntity.notFound().build()))
               .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
