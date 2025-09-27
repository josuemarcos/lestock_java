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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
@RestController
@RequestMapping("/material-types")
@RequiredArgsConstructor
public class MaterialTypeController implements GenericController{
    private final MaterialTypeMapper materialTypeMapper;
    private final MaterialTypeService materialTypeService;
    private final StockMapper stockMapper;
    private final StockService stockService;
    private final StockMovementMapper stockMovementMapper;
    private final StockMovementService stockMovementService;
    @PostMapping
    ResponseEntity<Void> saveMaterialType(@RequestBody @Valid MaterialTypeDTO materialTypeDTO) {
        MaterialType materialType = materialTypeMapper.toEntity(materialTypeDTO);
        materialTypeService.saveMaterialType(materialType);
        URI location = generateHeaderLocation(materialType.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    ResponseEntity<List<MaterialTypeDTO>> getAllMaterialTypes() {
        List<MaterialTypeDTO> materialTypes = materialTypeService.getMaterialTypes()
                .stream()
                .map(materialTypeMapper::toDTO)
                .toList();
        return ResponseEntity.ok(materialTypes);
    }

    @GetMapping("/{id}")
    ResponseEntity<MaterialTypeDTO> getMaterialType(@PathVariable Long id) {
        return materialTypeService.getMaterialType(id)
                .map(materialType -> {
                    MaterialTypeDTO materialTypeDTO = materialTypeMapper.toDTO(materialType);
                    return ResponseEntity.ok(materialTypeDTO);
                }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
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

    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteMaterialType(@PathVariable Long id) {
        return materialTypeService.getMaterialType(id)
                .map(
                        materialType -> {
                            materialTypeService.deleteMaterialType(materialType);
                            return ResponseEntity.noContent().build();
                        }
                ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/stock")
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

    @GetMapping("/{id}/stock")
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

    @GetMapping("/stock")
    ResponseEntity<List<GetStockDTO>> getAllStocks() {
        List<GetStockDTO> stockDTOS = stockService.getAllStocks()
                .stream()
                .map(stockMapper::toDTO)
                .toList();
        return ResponseEntity.ok(stockDTOS);
    }

    @PostMapping("/{id}/stock-movement")
    ResponseEntity<Object> saveStockMovement(@PathVariable Long id, @RequestBody SaveStockMovementDTO saveStockMovementDTO) {
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

    @GetMapping("/stock-movement")
    ResponseEntity<List<GetStockMovementDTO>> getAllStockMovements() {
        List<GetStockMovementDTO> stockMovementDTOS = stockMovementService.getAllStockMovements()
                .stream()
                .map(stockMovementMapper::toDTO)
                .toList();
        return ResponseEntity.ok(stockMovementDTOS);
    }

    @PutMapping("/{idMaterialType}/stock-movement/{idStockMovement}")
    ResponseEntity<Object> adjustStockMovement(@PathVariable Long idMaterialType, @PathVariable Long idStockMovement, @RequestBody SaveStockMovementDTO saveStockMovementDTO) {
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
