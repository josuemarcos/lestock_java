package com.example.lestock.controller.mapper;
import com.example.lestock.controller.dto.GetStockDTO;
import com.example.lestock.controller.dto.SaveStockDTO;
import com.example.lestock.dao.MaterialDAO;
import com.example.lestock.model.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = MaterialMapper.class)
public abstract class StockMapper {
    @Autowired
    MaterialDAO materialDAO;

    @Mapping(target = "material", expression = "java(materialDAO.findById(dto.materialId()).orElse(null))")
    public  abstract Stock toEntity(SaveStockDTO dto);

    public abstract GetStockDTO toDTO(Stock entity);
}
