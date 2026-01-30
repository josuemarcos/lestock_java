package com.example.lestock.controller.mapper.product;
import com.example.lestock.controller.dto.product.GetProductCostDTO;
import com.example.lestock.model.product.ProductCost;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ProductCostSummaryMapper.class)
public interface GetProductCostMapper {

    GetProductCostDTO toDTO(ProductCost pc);

}
