package com.example.lestock.controller.mapper.product;
import com.example.lestock.controller.dto.product.SaveProductDTO;
import com.example.lestock.model.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SaveProductCostMapper.class, SaveProductMaterialMapper.class})
public interface SaveProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productCosts", ignore = true)
    @Mapping(target = "productMaterials", ignore = true)
    Product toEntity(SaveProductDTO dto);
}
