package com.example.lestock.service.materialtype;
import com.example.lestock.controller.dto.stock.SaveMaterialTypeDTO;
import com.example.lestock.controller.mapper.stock.MaterialTypeMapper;
import com.example.lestock.dao.stock.MaterialTypeDAO;
import com.example.lestock.model.stock.MaterialType;
import com.example.lestock.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialTypeService {
    private final MaterialTypeDAO materialTypeDAO;
    private final MaterialTypeValidationService materialTypeValidationService;
    private final MaterialTypeMapper materialTypeMapper;

    @Transactional
    public MaterialType saveMaterialType(MaterialType materialType, User loggedUser) {
        materialTypeValidationService.validateMaterialType(materialType);
        materialType.setUserId(loggedUser.getId());
        return materialTypeDAO.save(materialType);
    }

    public List<MaterialType> getMaterialTypes() {
        return materialTypeDAO.findAll();
    }

    public MaterialType getMaterialType(Long materialTypeId) {
        return materialTypeValidationService.getExistingMaterialType(materialTypeId);
    }

    @Transactional
    public void updateMaterialType(Long materialTypeId, SaveMaterialTypeDTO updatedMaterialType, User loggedUser) {
        MaterialType materialType = materialTypeValidationService.getExistingMaterialType(materialTypeId);
        materialTypeMapper.updateEntity(materialType, updatedMaterialType);
        materialType.setUserId(loggedUser.getId());
        materialTypeValidationService.validateMaterialType(materialType);
        materialTypeDAO.save(materialType);
    }

    @Transactional
    public void  deleteMaterialType(Long materialTypeId) {
        MaterialType materialType = materialTypeValidationService.getExistingMaterialType(materialTypeId);
        materialTypeDAO.delete(materialType);
    }

}
