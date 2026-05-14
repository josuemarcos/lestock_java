package com.example.lestock.controller.mapper.stock;
import com.example.lestock.controller.dto.stock.GetStockMovementDTO;
import com.example.lestock.controller.dto.stock.SaveStockMovementDTO;
import com.example.lestock.dao.stock.SupplierDAO;
import com.example.lestock.model.stock.StockMovement;
import com.example.lestock.model.stock.Supplier;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = StockMapper.class)
public interface StockMovementMapper {

    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "stock", ignore = true)
    StockMovement toEntity(SaveStockMovementDTO dto);

    @Mapping(target = "materialType", source = "stock.materialType")
    GetStockMovementDTO toDTO(StockMovement entity);

    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "stock", ignore = true)
    void updateEntity(
            @MappingTarget StockMovement entity,
            SaveStockMovementDTO dto
    );
}
