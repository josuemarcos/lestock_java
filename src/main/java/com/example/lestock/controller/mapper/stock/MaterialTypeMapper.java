package com.example.lestock.controller.mapper.stock;

import com.example.lestock.controller.dto.stock.MaterialTypeDTO;
import com.example.lestock.model.stock.MaterialType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaterialTypeMapper {
    MaterialType toEntity(MaterialTypeDTO dto);
    MaterialTypeDTO toDTO(MaterialType entity);
}
