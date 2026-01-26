package com.example.lestock.controller.mapper.stock;
import com.example.lestock.controller.dto.stock.GetMaterialDTO;
import com.example.lestock.controller.dto.stock.SaveMaterialDTO;
import com.example.lestock.dao.stock.MaterialTypeDAO;
import com.example.lestock.dao.stock.SupplierDAO;
import com.example.lestock.model.stock.Material;
import com.example.lestock.model.stock.MaterialType;
import com.example.lestock.model.stock.Supplier;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {SupplierMapper.class, MaterialTypeMapper.class})
public abstract class MaterialMapper {
    @Autowired
    SupplierDAO supplierDAO;
    @Autowired
    MaterialTypeDAO materialTypeDAO;

    public abstract Material toEntity(SaveMaterialDTO dto);
    public abstract GetMaterialDTO toDTO(Material entity);

    @Mappings({
            @Mapping(target = "supplier", ignore = true),
            @Mapping(target = "materialType", ignore = true)
    })
    public abstract void updateEntity(@MappingTarget Material entity, SaveMaterialDTO dto);

    @AfterMapping
    protected void resolveRelationships(SaveMaterialDTO dto, @MappingTarget Material entity) {
        if(dto.IdSupplier() != null) {
            Supplier supplier = supplierDAO.findById(dto.IdSupplier()).orElse(null);
            entity.setSupplier(supplier);
        }
        if(dto.IdMaterialType() != null) {
            MaterialType materialType = materialTypeDAO.findById(dto.IdMaterialType()).orElse(null);
            entity.setMaterialType(materialType);
        }
    }
}
