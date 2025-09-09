package com.example.lestock.service;
import com.example.lestock.dao.MaterialTypeDAO;
import com.example.lestock.model.MaterialType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialTypeService {
    private final MaterialTypeDAO materialTypeDAO;

    public void saveMaterialType(MaterialType materialType) {
        materialTypeDAO.save(materialType);
    }

}
