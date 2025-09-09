package com.example.lestock.controller;
import com.example.lestock.controller.dto.MaterialTypeDTO;
import com.example.lestock.controller.mapper.MaterialTypeMapper;
import com.example.lestock.model.MaterialType;
import com.example.lestock.service.MaterialTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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

}
