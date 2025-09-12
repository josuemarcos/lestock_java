package com.example.lestock.service;
import com.example.lestock.dao.MaterialDAO;
import com.example.lestock.model.Material;
import com.example.lestock.validator.MaterialValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialDAO materialDAO;
    private final MaterialValidator materialValidator;

    public void saveMaterial(Material material) {
        materialValidator.validateMaterial(material);
        materialDAO.save(material);
    }

    public List<Material> getAllMaterials() {
        return materialDAO.findAll();
    }

    public Optional<Material> getMaterialById(Long id) {
        return materialDAO.findById(id);
    }

    public void updateMaterial(Material material) {
        materialValidator.validateMaterial(material);
        materialDAO.save(material);
    }

    public void deleteMaterial(Material material) {
        materialDAO.delete(material);
    }

}
