package com.example.lestock.controller;
import com.example.lestock.controller.dto.GetStockDTO;
import com.example.lestock.controller.dto.MaterialTypeDTO;
import com.example.lestock.controller.dto.SaveStockDTO;
import com.example.lestock.controller.dto.SaveStockMovementDTO;
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
    ResponseEntity<Void> saveStock(@PathVariable Long id, @RequestBody SaveStockDTO saveStockDTO) {
        SaveStockDTO stockDTO = new SaveStockDTO(saveStockDTO.currentQuantity(), saveStockDTO.averageCost(), id);
        Stock stockEntity = stockMapper.toEntity(stockDTO);
        stockService.saveStock(stockEntity);
        URI location = generateHeaderLocation(stockEntity.getId());
        return ResponseEntity.created(location).build();
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
    ResponseEntity<Void> saveStockMovement(@PathVariable Long id, @RequestBody SaveStockMovementDTO saveStockMovementDTO) {
        StockMovement stockMovement = stockMovementMapper.toEntity(saveStockMovementDTO);
        stockMovementService.updateStock(stockMovement);
        stockMovementService.save(stockMovement);
        URI location = generateHeaderLocation(stockMovement.getId());
        return ResponseEntity.created(location).build();
    }
    

}
