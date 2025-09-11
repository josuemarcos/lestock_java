package com.example.lestock.controller;

import com.example.lestock.controller.dto.GetMaterialDTO;
import com.example.lestock.controller.dto.SaveMaterialDTO;
import com.example.lestock.controller.mapper.MaterialMapper;
import com.example.lestock.model.Material;
import com.example.lestock.service.MaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/materials")
public class MaterialController implements GenericController {
    private final MaterialService materialService;
    private final MaterialMapper materialMapper;

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
}
