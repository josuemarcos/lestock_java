package com.example.lestock.controller.mapper;
import com.example.lestock.controller.dto.GetMaterialDTO;
import com.example.lestock.controller.dto.SaveMaterialDTO;
import com.example.lestock.dao.MaterialTypeDAO;
import com.example.lestock.dao.SupplierDAO;
import com.example.lestock.model.Material;
import com.example.lestock.model.MaterialType;
import com.example.lestock.model.Supplier;
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
