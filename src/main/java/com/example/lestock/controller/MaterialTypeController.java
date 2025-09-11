package com.example.lestock.controller;
import com.example.lestock.controller.dto.MaterialTypeDTO;
import com.example.lestock.controller.mapper.MaterialTypeMapper;
import com.example.lestock.model.MaterialType;
import com.example.lestock.service.MaterialTypeService;
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
        MaterialTypeDTO materialTypeDTOWithId = new MaterialTypeDTO(
                id,
                materialTypeDTO.name(),
                materialTypeDTO.metricUnit()
        );
        return materialTypeService.getMaterialType(id)
                .map(
                        materialType -> {
                            materialType.setName(materialTypeDTOWithId.name());
                            materialType.setMetricUnit(materialTypeDTOWithId.metricUnit());
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

}
