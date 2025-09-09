package com.example.lestock.service;
import com.example.lestock.dao.MaterialTypeDAO;
import com.example.lestock.model.MaterialType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialTypeService {
    private final MaterialTypeDAO materialTypeDAO;

    public void saveMaterialType(MaterialType materialType) {
        materialTypeDAO.save(materialType);
    }

    public List<MaterialType> getMaterialTypes() {
        return materialTypeDAO.findAll();
    }

    public Optional<MaterialType> getMaterialType(Long id) {
        return materialTypeDAO.findById(id);
    }

}
