package com.example.lestock.controller.mapper.product;
import com.example.lestock.controller.dto.product.ProductMaterialSummaryDTO;
import com.example.lestock.model.stock.Material;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMaterialSummaryMapper {
    @Mapping(source = "materialType.name",  target = "name")
    @Mapping(source = "materialType.metricUnit", target = "metricUnit")
    ProductMaterialSummaryDTO dto(Material material);
}
