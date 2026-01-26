package com.example.lestock.service.stock;
import com.example.lestock.dao.stock.MaterialDAO;
import com.example.lestock.model.stock.Material;
import com.example.lestock.model.User;
import com.example.lestock.security.SecurityService;
import com.example.lestock.validator.stock.MaterialValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialDAO materialDAO;
    private final MaterialValidator materialValidator;
    private final SecurityService securityService;

    public void saveMaterial(Material material) {
        materialValidator.validateMaterial(material);
        User user = securityService.getLoggedUser();
        material.setUserId(user.getId());
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
        User user = securityService.getLoggedUser();
        material.setUserId(user.getId());
        materialDAO.save(material);
    }

    public void deleteMaterial(Material material) {
        materialDAO.delete(material);
    }

}
