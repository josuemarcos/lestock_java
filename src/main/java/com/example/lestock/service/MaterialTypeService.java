package com.example.lestock.service;
import com.example.lestock.dao.MaterialTypeDAO;
import com.example.lestock.model.MaterialType;
import com.example.lestock.validator.MaterialTypeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialTypeService {
    private final MaterialTypeDAO materialTypeDAO;
    private final MaterialTypeValidator materialTypeValidator;

    public void saveMaterialType(MaterialType materialType) {
        materialTypeValidator.validateMaterialType(materialType);
        materialTypeDAO.save(materialType);
    }

    public List<MaterialType> getMaterialTypes() {
        return materialTypeDAO.findAll();
    }

    public Optional<MaterialType> getMaterialType(Long id) {
        return materialTypeDAO.findById(id);
    }

    public void updateMaterialType(MaterialType materialType) {
        materialTypeValidator.validateMaterialType(materialType);
        materialTypeDAO.save(materialType);
    }

    public void  deleteMaterialType(MaterialType materialType) {
        materialTypeDAO.delete(materialType);
    }

}
