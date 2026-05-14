package com.example.lestock.controller.mapper.stock;

import com.example.lestock.controller.dto.stock.GetMaterialTypeDTO;
import com.example.lestock.controller.dto.stock.SaveMaterialTypeDTO;
import com.example.lestock.model.stock.MaterialType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MaterialTypeMapper {
    MaterialType toEntity(SaveMaterialTypeDTO dto);
    GetMaterialTypeDTO toDTO(MaterialType entity);
    void updateEntity(@MappingTarget MaterialType entity, SaveMaterialTypeDTO dto);
}
