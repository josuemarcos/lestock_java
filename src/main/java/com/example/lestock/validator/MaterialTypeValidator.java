package com.example.lestock.validator;
import com.example.lestock.controller.dto.errors.FieldErrorDTO;
import com.example.lestock.dao.MaterialTypeDAO;
import com.example.lestock.exceptions.DuplicateRecordException;
import com.example.lestock.model.MaterialType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialTypeValidator  {
    private final MaterialTypeDAO materialTypeDAO;

    public void validateMaterialType(MaterialType materialType) {

        if(isMaterialTypeSaved(materialType)) {
            FieldErrorDTO fieldErrorName = new FieldErrorDTO(
                    "name",
                    "Material type with name and brand pair already exists",
                    "UniquenessBreak"
            );
            FieldErrorDTO fieldErrorBrand = new FieldErrorDTO(
                    "brand",
                    "Material type with name and brand pair already exists",
                    "UniquenessBreak"
            );
            throw new DuplicateRecordException("Material type already exists", List.of(fieldErrorName, fieldErrorBrand));
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
