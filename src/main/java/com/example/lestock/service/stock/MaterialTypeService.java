package com.example.lestock.service.stock;
import com.example.lestock.dao.stock.MaterialTypeDAO;
import com.example.lestock.model.stock.MaterialType;
import com.example.lestock.model.User;
import com.example.lestock.security.SecurityService;
import com.example.lestock.validator.stock.MaterialTypeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialTypeService {
    private final MaterialTypeDAO materialTypeDAO;
    private final MaterialTypeValidator materialTypeValidator;
    private final SecurityService securityService;

    public void saveMaterialType(MaterialType materialType) {
        materialTypeValidator.validateMaterialType(materialType);
        User user = securityService.getLoggedUser();
        materialType.setUserId(user.getId());
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
        User user = securityService.getLoggedUser();
        materialType.setUserId(user.getId());
        materialTypeDAO.save(materialType);
    }

    public void  deleteMaterialType(MaterialType materialType) {
        materialTypeDAO.delete(materialType);
    }

}
