package com.example.lestock.controller.stock;
import com.example.lestock.controller.common.GenericController;
import com.example.lestock.controller.dto.stock.GetMaterialDTO;
import com.example.lestock.controller.dto.stock.SaveMaterialDTO;
import com.example.lestock.controller.mapper.stock.MaterialMapper;
import com.example.lestock.controller.mapper.stock.StockMapper;
import com.example.lestock.model.stock.Material;
import com.example.lestock.service.stock.MaterialService;
import com.example.lestock.service.stock.MaterialTypeService;
import com.example.lestock.service.stock.StockService;
import com.example.lestock.service.stock.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    ResponseEntity<Void> saveMaterial(@RequestBody @Valid SaveMaterialDTO materialDTO) {
        Material materialEntity = materialMapper.toEntity(materialDTO);
        materialService.saveMaterial(materialEntity);
        URI location = generateHeaderLocation(materialEntity.getId());
        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    ResponseEntity<List<GetMaterialDTO>> getAllMaterials() {
        List<GetMaterialDTO> materials = materialService.getAllMaterials()
                .stream()
                .map(materialMapper::toDTO)
                .toList();
        return ResponseEntity.ok(materials);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    ResponseEntity<Object> updateMaterial(@PathVariable Long id, @RequestBody @Valid SaveMaterialDTO materialDTO) {
        return materialService.getMaterialById(id)
                .map(material -> {
                    materialMapper.updateEntity(material, materialDTO);
                    materialService.updateMaterial(material);
                    return ResponseEntity.ok().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
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
