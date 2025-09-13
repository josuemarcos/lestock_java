package com.example.lestock.validator;
import com.example.lestock.controller.dto.MaterialTypeDTO;
import com.example.lestock.controller.mapper.MaterialTypeMapper;
import com.example.lestock.dao.MaterialTypeDAO;
import com.example.lestock.exceptions.DuplicateRecordException;
import com.example.lestock.model.MaterialType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialTypeValidator  {
    private final MaterialTypeDAO materialTypeDAO;

    public void validateMaterialType(MaterialType materialType) {

        if(isMaterialTypeSaved(materialType)) {
            throw new DuplicateRecordException("Material type already exists");
        }
    }

    private Boolean isMaterialTypeSaved(MaterialType materialType) {
        Optional<MaterialType> materialTypeOptional = materialTypeDAO.findByNameAndBrand(materialType.getName(), materialType.getBrand());

        if (materialType.getId() == null) {
            return materialTypeOptional.isPresent();
        }

        return materialTypeOptional
                .map(MaterialType::getId)
                .stream()
                .anyMatch(
                        id -> !id.equals(materialType.getId())
                );
    }
}
