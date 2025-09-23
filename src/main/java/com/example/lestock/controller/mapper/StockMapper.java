package com.example.lestock.controller.mapper;
import com.example.lestock.controller.dto.GetStockDTO;
import com.example.lestock.controller.dto.SaveStockDTO;
import com.example.lestock.dao.MaterialTypeDAO;
import com.example.lestock.model.MaterialType;
import com.example.lestock.model.Stock;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = MaterialTypeMapper.class)
public abstract class StockMapper {
    @Autowired
    MaterialTypeDAO materialTypeDAO;

    public  abstract Stock toEntity(SaveStockDTO dto);
    public abstract GetStockDTO toDTO(Stock entity);

    @Mapping(target = "materialType", ignore = true)
    public abstract void updateStock(@MappingTarget Stock stock, SaveStockDTO dto);

    @AfterMapping
    protected void resolveRelationships(@MappingTarget Stock stock, SaveStockDTO dto) {
        if(dto.materialTypeId() != null) {
            MaterialType materialType = materialTypeDAO.findById(dto.materialTypeId()).orElse(null);
            stock.setMaterialType(materialType);
        }
    }
}
