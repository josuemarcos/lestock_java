package com.example.lestock.validator;
import com.example.lestock.controller.dto.SaveStockMovementDTO;
import com.example.lestock.model.MovementType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockMovementValidator implements ConstraintValidator<ValidStockMovement, SaveStockMovementDTO> {
    @Override
    public boolean isValid(SaveStockMovementDTO stockMovementDTO, ConstraintValidatorContext context) {
        if(stockMovementDTO == null) return true;
        boolean valid = true;
        context.disableDefaultConstraintViolation();
        return valid;
    }
}
