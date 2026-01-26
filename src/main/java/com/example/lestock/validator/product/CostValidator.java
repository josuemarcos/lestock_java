package com.example.lestock.validator.product;
import com.example.lestock.controller.dto.errors.FieldErrorDTO;
import com.example.lestock.dao.product.CostDAO;
import com.example.lestock.exceptions.DuplicateRecordException;
import com.example.lestock.model.product.Cost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CostValidator {
    private final CostDAO costDAO;

    public void validateCost(Cost cost) {
        if(isCostSaved(cost)) {
            FieldErrorDTO fieldErrorDTO = new FieldErrorDTO(
                    "name",
                    "Cost already registered",
                    "UniquenessBreak"
            );
            throw new DuplicateRecordException("Cost already registered", List.of(fieldErrorDTO));
        }
    }

    private boolean isCostSaved(Cost cost) {
        Optional<Cost> foundCost = costDAO.findByName(cost.getName());

        if(cost.getId() == null) {
            return foundCost.isPresent();
        }

        return foundCost
                .map(Cost::getId)
                .stream()
                .anyMatch(id -> !id.equals(cost.getId()));
    }
}
