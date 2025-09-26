package com.example.lestock.controller.mapper;
import com.example.lestock.controller.dto.GetStockDTO;
import com.example.lestock.controller.dto.SaveStockDTO;
import com.example.lestock.model.Stock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class StockMapper {

    public  abstract Stock toEntity(SaveStockDTO dto);
    public abstract GetStockDTO toDTO(Stock entity);

}
