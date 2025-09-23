package com.example.lestock.controller;
import com.example.lestock.controller.dto.GetMaterialDTO;
import com.example.lestock.controller.dto.SaveMaterialDTO;
import com.example.lestock.controller.mapper.MaterialMapper;
import com.example.lestock.controller.mapper.StockMapper;
import com.example.lestock.model.Material;
import com.example.lestock.model.MaterialType;
import com.example.lestock.model.Supplier;
import com.example.lestock.service.MaterialService;
import com.example.lestock.service.MaterialTypeService;
import com.example.lestock.service.StockService;
import com.example.lestock.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/materials")
public class MaterialController implements GenericController {
    private final MaterialService materialService;
    private final MaterialMapper materialMapper;
    private final SupplierService supplierService;
    private final MaterialTypeService materialTypeService;
    private final StockService stockService;
    private final StockMapper stockMapper;

    @PostMapping
    ResponseEntity<Void> saveMaterial(@RequestBody @Valid SaveMaterialDTO materialDTO) {
        Material materialEntity = materialMapper.toEntity(materialDTO);
        materialService.saveMaterial(materialEntity);
        URI location = generateHeaderLocation(materialEntity.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    ResponseEntity<List<GetMaterialDTO>> getAllMaterials() {
        List<GetMaterialDTO> materials = materialService.getAllMaterials()
                .stream()
                .map(materialMapper::toDTO)
                .toList();
        return ResponseEntity.ok(materials);
    }

    @GetMapping("/{id}")
    ResponseEntity<GetMaterialDTO> getMaterial(@PathVariable Long id) {
        return materialService.getMaterialById(id)
                .map(
                        material -> {
                            GetMaterialDTO materialDTO = materialMapper.toDTO(material);
                            return ResponseEntity.ok(materialDTO);
                        }
                ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<Object> updateMaterial(@PathVariable Long id, @RequestBody @Valid SaveMaterialDTO materialDTO) {
        return materialService.getMaterialById(id)
                .map(material -> {
                    materialMapper.updateEntity(material, materialDTO);
                    materialService.updateMaterial(material);
                    return ResponseEntity.ok().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteMaterial(@PathVariable Long id) {
        return materialService.getMaterialById(id)
                .map(
                        material -> {
                            materialService.deleteMaterial(material);
                            return ResponseEntity.noContent().build();
                        }
                ).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
