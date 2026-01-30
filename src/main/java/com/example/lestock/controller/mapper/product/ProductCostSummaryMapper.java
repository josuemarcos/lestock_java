package com.example.lestock.controller.mapper.product;

import com.example.lestock.controller.dto.product.ProductCostSummaryDTO;
import com.example.lestock.model.product.Cost;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductCostSummaryMapper {
    ProductCostSummaryDTO dto(Cost cost);
}
