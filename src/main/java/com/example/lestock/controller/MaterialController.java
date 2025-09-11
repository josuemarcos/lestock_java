package com.example.lestock.controller;

import com.example.lestock.controller.dto.SaveMaterialDTO;
import com.example.lestock.controller.mapper.MaterialMapper;
import com.example.lestock.model.Material;
import com.example.lestock.service.MaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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
}
