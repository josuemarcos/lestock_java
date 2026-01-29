package com.example.lestock.controller.mapper.product;

import com.example.lestock.controller.dto.product.GetProductMaterialDTO;
import com.example.lestock.controller.mapper.stock.MaterialMapper;
import com.example.lestock.model.product.ProductMaterial;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = MaterialMapper.class)
public interface GetProductMaterialMapper {

    GetProductMaterialDTO toDTO(ProductMaterial pm);
}
