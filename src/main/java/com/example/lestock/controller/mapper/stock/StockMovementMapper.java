package com.example.lestock.controller.mapper.stock;
import com.example.lestock.controller.dto.stock.GetStockMovementDTO;
import com.example.lestock.controller.dto.stock.SaveStockMovementDTO;
import com.example.lestock.dao.stock.SupplierDAO;
import com.example.lestock.model.stock.StockMovement;
import com.example.lestock.model.stock.Supplier;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = StockMapper.class)
public abstract class StockMovementMapper {

    @Autowired
    SupplierDAO supplierDAO;

    public abstract StockMovement toEntity(SaveStockMovementDTO saveStockMovementDTO);
    @Mapping(target = "materialType", source = "stock.materialType")
    public abstract GetStockMovementDTO toDTO(StockMovement stockMovement);

    @Mapping(target = "supplier", ignore = true)
    public abstract void updateStockMovement(@MappingTarget StockMovement stockMovement, SaveStockMovementDTO saveStockMovementDTO);

    @AfterMapping
    protected void resolveRelationships(@MappingTarget StockMovement stockMovement, SaveStockMovementDTO dto) {

        if(dto.supplierId() != null) {
            Supplier supplier = supplierDAO.findById(dto.supplierId()).orElse(null);
            stockMovement.setSupplier(supplier);
        }
    }

}
