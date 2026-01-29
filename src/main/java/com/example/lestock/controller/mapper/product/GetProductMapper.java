package com.example.lestock.controller.mapper.product;

import com.example.lestock.controller.dto.product.GetProductDTO;
import com.example.lestock.model.product.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {GetProductCostMapper.class, GetProductMaterialMapper.class})
public interface GetProductMapper {
    GetProductDTO toDto(Product entity);
}
