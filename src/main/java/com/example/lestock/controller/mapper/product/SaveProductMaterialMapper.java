package com.example.lestock.controller.mapper.product;
import com.example.lestock.controller.dto.product.SaveProductMaterialDTO;
import com.example.lestock.dao.stock.MaterialDAO;
import com.example.lestock.model.product.ProductMaterial;
import com.example.lestock.model.stock.Material;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class SaveProductMaterialMapper {

    @Autowired
    MaterialDAO materialDAO;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "material", ignore = true)
    public abstract ProductMaterial toEntity(SaveProductMaterialDTO dto);

    @AfterMapping
    protected void resolveMaterial(SaveProductMaterialDTO dto, @MappingTarget ProductMaterial entity) {
        if(dto.materialId() != null) {
            Material material = materialDAO.findById(dto.materialId()).orElse(null);
            entity.setMaterial(material);
        }
    }

}
