package com.example.lestock.controller.mapper;
import com.example.lestock.controller.dto.GetStockDTO;
import com.example.lestock.controller.dto.SaveStockDTO;
import com.example.lestock.dao.MaterialTypeDAO;
import com.example.lestock.model.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = MaterialTypeMapper.class)
public abstract class StockMapper {
    @Autowired
    MaterialTypeDAO materialTypeDAO;

    @Mapping(target = "materialType", expression = "java(materialTypeDAO.findById(dto.materialTypeId()).orElse(null))")
    public  abstract Stock toEntity(SaveStockDTO dto);

    public abstract GetStockDTO toDTO(Stock entity);
}
