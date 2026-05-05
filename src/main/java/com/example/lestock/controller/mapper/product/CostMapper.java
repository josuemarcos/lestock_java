package com.example.lestock.controller.mapper.product;
import com.example.lestock.controller.dto.product.GetCostDTO;
import com.example.lestock.controller.dto.product.SaveCostDTO;
import com.example.lestock.model.product.Cost;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CostMapper {
    Cost toEntity(SaveCostDTO costDTO);
    GetCostDTO toDTO(Cost cost);
}
