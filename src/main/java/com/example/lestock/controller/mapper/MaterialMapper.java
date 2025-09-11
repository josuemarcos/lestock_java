package com.example.lestock.controller.mapper;
import com.example.lestock.controller.dto.GetMaterialDTO;
import com.example.lestock.controller.dto.SaveMaterialDTO;
import com.example.lestock.dao.MaterialTypeDAO;
import com.example.lestock.dao.SupplierDAO;
import com.example.lestock.model.Material;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {SupplierMapper.class, MaterialTypeMapper.class})
public abstract class MaterialMapper {
    @Autowired
    SupplierDAO supplierDAO;
    @Autowired
    MaterialTypeDAO materialTypeDAO;

    @Mapping(target = "supplier", expression = "java(supplierDAO.findById(dto.IdSupplier()).orElse(null))")
    @Mapping(target = "materialType", expression = "java(materialTypeDAO.findById(dto.IdMaterialType()).orElse(null))")
    public abstract Material toEntity(SaveMaterialDTO dto);

    public abstract GetMaterialDTO toDTO(Material entity);

}
