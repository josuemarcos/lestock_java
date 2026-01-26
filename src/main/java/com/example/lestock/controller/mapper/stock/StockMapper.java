package com.example.lestock.controller.mapper.stock;
import com.example.lestock.controller.dto.stock.GetStockDTO;
import com.example.lestock.controller.dto.stock.SaveStockDTO;
import com.example.lestock.model.stock.Stock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class StockMapper {

    public  abstract Stock toEntity(SaveStockDTO dto);
    public abstract GetStockDTO toDTO(Stock entity);

}
