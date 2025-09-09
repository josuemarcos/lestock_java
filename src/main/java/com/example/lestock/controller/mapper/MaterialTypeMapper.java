package com.example.lestock.controller.mapper;

import com.example.lestock.controller.dto.MaterialTypeDTO;
import com.example.lestock.model.MaterialType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaterialTypeMapper {
    MaterialType toEntity(MaterialTypeDTO dto);
    MaterialTypeDTO toDTO(MaterialType entity);
}
