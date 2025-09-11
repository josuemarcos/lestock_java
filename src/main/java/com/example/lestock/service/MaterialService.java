package com.example.lestock.service;
import com.example.lestock.dao.MaterialDAO;
import com.example.lestock.model.Material;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialDAO materialDAO;

    public void saveMaterial(Material material) {
        materialDAO.save(material);
    }

    public List<Material> getAllMaterials() {
        return materialDAO.findAll();
    }

}
