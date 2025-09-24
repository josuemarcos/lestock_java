package com.example.lestock.controller.mapper;
import com.example.lestock.controller.dto.GetStockDTO;
import com.example.lestock.controller.dto.SaveStockMovementDTO;
import com.example.lestock.dao.StockDAO;
import com.example.lestock.dao.SupplierDAO;
import com.example.lestock.model.Stock;
import com.example.lestock.model.StockMovement;
import com.example.lestock.model.Supplier;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = StockMapper.class)
public abstract class StockMovementMapper {
    @Autowired
    StockDAO stockDAO;

    @Autowired
    SupplierDAO supplierDAO;

    public abstract StockMovement toEntity(SaveStockMovementDTO saveStockMovementDTO);
    public abstract GetStockDTO toDTO(StockMovement stockMovement);

    @Mappings({
            @Mapping(target ="stock",ignore =true),
            @Mapping(target = "supplier", ignore = true)
    })
    public abstract void updateStockMovement(@MappingTarget StockMovement stockMovement, SaveStockMovementDTO saveStockMovementDTO);

    @AfterMapping
    protected void resolveRelationships(@MappingTarget StockMovement stockMovement, SaveStockMovementDTO dto) {
        if(dto.stockId() != null) {
            Stock stock = stockDAO.findById(dto.stockId()).orElse(null);
            stockMovement.setStock(stock);
        }
        if(dto.supplierId() != null) {
            Supplier supplier = supplierDAO.findById(dto.supplierId()).orElse(null);
            stockMovement.setSupplier(supplier);
        }
    }


}
