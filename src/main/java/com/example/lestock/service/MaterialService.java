package com.example.lestock.service;
import com.example.lestock.dao.MaterialDAO;
import com.example.lestock.model.Material;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    public Optional<Material> getMaterialById(Long id) {
        return materialDAO.findById(id);
    }

}
