package com.example.lestock.controller.mapper.product;
import com.example.lestock.controller.dto.product.CostDTO;
import com.example.lestock.model.product.Cost;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CostMapper {
    Cost toEntity(CostDTO costDTO);
    CostDTO toDTO(Cost cost);
}
